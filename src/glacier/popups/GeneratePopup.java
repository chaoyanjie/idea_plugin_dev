/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package glacier.popups;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.wm.impl.ToolWindowImpl;
import glacier.action.PingTool;
import glacier.toolwindow.PingToolWindowFactory;

/**
 * Created by shuifeng.lsf on 2/16/17.
 *
 * @author shuifeng.lsf
 * @date 2017/02/16
 */
public class GeneratePopup extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        ActionManager.getInstance().getAction(PingTool.ActionId)
                .actionPerformed(anActionEvent);
    }

    @Override
    public void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        DataContext dataContext = e.getDataContext();
        ToolWindowImpl toolWindow = (ToolWindowImpl) PlatformDataKeys.TOOL_WINDOW.getData(dataContext);
        if (toolWindow == null || !toolWindow.getId().equals(PingToolWindowFactory.toolWindowId)) {
            presentation.setVisible(false);
            presentation.setEnabled(false);
        }
    }
}
