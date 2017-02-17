/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package glacier.popups;

import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.impl.ToolWindowImpl;
import com.intellij.ui.content.Content;
import glacier.action.PingTool;
import glacier.toolwindow.PingToolWindowFactory;

/**
 * Created by shuifeng.lsf on 2/16/17.
 *
 * @author shuifeng.lsf
 * @date 2017/02/16
 */
public class StopPingMenuItem extends AnAction {

    public StopPingMenuItem() {
        super("Ping", "Plugin Demo", IconLoader.getIcon("/icons/ping.png"));
    }

    @Override
    public void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        DataContext dataContext = e.getDataContext();
        ToolWindowImpl toolWindow = (ToolWindowImpl) PlatformDataKeys.TOOL_WINDOW.getData(dataContext);
        if (!toolWindow.getId().equals(PingToolWindowFactory.toolWindowId)) {
            presentation.setEnabled(false);
            presentation.setVisible(false);
        }
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        ToolWindow toolWindow = PlatformDataKeys.TOOL_WINDOW.getData(anActionEvent.getDataContext());
        Content content = toolWindow.getContentManager().getSelectedContent();
        ConsoleViewImpl consoleView = PingTool.pingConsoleViewer.get(content);
        ProcessHandler processHandler = PingTool.pingExecutions.get(consoleView);
        if (processHandler != null) {
            processHandler.destroyProcess();
            PingTool.pingExecutions.put(consoleView, null);
            consoleView.print("Stop!!!\n", ConsoleViewContentType.SYSTEM_OUTPUT);
        }
    };
}
