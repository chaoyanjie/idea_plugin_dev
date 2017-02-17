/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package glacier.popups;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.impl.ToolWindowImpl;
import glacier.toolwindow.PingToolWindowFactory;

/**
 * Created by shuifeng.lsf on 2/16/17.
 *
 * @author shuifeng.lsf
 * @date 2017/02/16
 */
public class ActionGroupPopup extends AnAction{
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        JBPopupFactory jbPopupFactory = JBPopupFactory.getInstance();
        ActionGroup allActionInPing = (ActionGroup) ActionManager.getInstance().getAction("Plugin.Demo.Ping.Popup");
        jbPopupFactory.createActionGroupPopup("All Action In Ping Demo",
                                            allActionInPing,
                                            anActionEvent.getDataContext(),
                                            JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                                            false).showInBestPositionFor(anActionEvent.getDataContext());
    }

    @Override
    public void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        DataContext dataContext = e.getDataContext();
        ToolWindowImpl toolWindow = (ToolWindowImpl) PlatformDataKeys.TOOL_WINDOW.getData(dataContext);
        if (!toolWindow.getId().equals(PingToolWindowFactory.toolWindowId)) {
            presentation.setEnabled(false);
        }
    }
}
