package glacier.action;


import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.impl.ToolWindowImpl;
import com.intellij.ui.content.Content;
import glacier.dialog.PingDialog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shuifeng.lsf on 2/16/17.
 *
 * @author shuifeng.lsf
 * @date 2017/02/16
 */
public class PingTool extends AnAction
{
    public static final String ActionId = "Plugin.Demo.Ping";

    public static Map<Content, ConsoleViewImpl> pingConsoleViewer = new HashMap<Content, ConsoleViewImpl>();
    public static Map<ConsoleViewImpl, ProcessHandler> pingExecutions = new HashMap<ConsoleViewImpl, ProcessHandler>();

    public PingTool()
    {
        // set the menu name, description and icon.
        super("Ping", "Plugin Demo", IconLoader.getIcon("/icons/ping.png"));
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent)
    {
        Project project = anActionEvent.getProject();

        PingDialog pingDialog = new PingDialog(project, false);
        if (!pingDialog.showAndGet()) {
            return;
        }

        ProcessHandler processHandler = getProcessHandle(pingDialog.getIpAddress());

        final String toolID = "Ping";
        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow(toolID);
        toolWindow.show(() -> {
            if (processHandler != null) {
                Content content = toolWindow.getContentManager().getSelectedContent();
                ConsoleViewImpl consoleView = pingConsoleViewer.get(content);

                if (consoleView.isRunning()) {
                    return;
                }

                consoleView.attachToProcess(processHandler);
                pingExecutions.put(consoleView, processHandler);
                processHandler.startNotify();
            }
        });
    }

    private ProcessHandler getProcessHandle(String param)
    {
        GeneralCommandLine generalCommandLine = new GeneralCommandLine()
                .withWorkDirectory(System.getProperty("user.home"))
                .withExePath("ping")
                .withParameters(param);
        try {
            ProcessHandler processHandler = new OSProcessHandler(
                    generalCommandLine.createProcess(),
                    generalCommandLine.getCommandLineString(),
                    generalCommandLine.getCharset());

            return processHandler;
        }
        catch (Exception ex) {
            return null;
        }
    }
}

