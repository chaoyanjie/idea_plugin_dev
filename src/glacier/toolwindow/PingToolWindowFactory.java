package glacier.toolwindow;

import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import glacier.action.PingTool;
import org.jetbrains.annotations.NotNull;

/**
 * Created by shuifeng.lsf on 2/16/17.
 *
 * @author shuifeng.lsf
 * @date 2017/02/16
 */
public class PingToolWindowFactory implements ToolWindowFactory, Condition<Project> {

    public static final String toolWindowId = "Ping";

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        addPingExecution(project, toolWindow);
    }

    public static ActionToolbar createToolBar(Project project, ConsoleViewImpl consoleView) {
        DefaultActionGroup defaultActionGroup = new DefaultActionGroup();
        AnAction[] actions = consoleView.createConsoleActions();
        for (AnAction action : actions) {
            defaultActionGroup.add(action);
        }
        return ActionManager.getInstance().createActionToolbar("unknown", defaultActionGroup, false);
    }

    public static void addPingExecution(Project project, ToolWindow toolWindow) {
        ConsoleViewImpl consoleView= new ConsoleViewImpl(project,true);

        SimpleToolWindowPanel toolWindowPanel = new SimpleToolWindowPanel(false, true);
        toolWindowPanel.setContent(consoleView.getComponent());

        ActionToolbar actionToolbar = createToolBar(project, consoleView);
        actionToolbar.setTargetComponent(consoleView.getComponent());
        toolWindowPanel.setToolbar(actionToolbar.getComponent());

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(toolWindowPanel, "", false);

        toolWindow.getContentManager().addContent(content);

        PingTool.pingConsoleViewer.put(content, consoleView);
        PingTool.pingExecutions.put(consoleView, null);
    }

    @Override
    public boolean value(Project project) {
        return true;
    }
}