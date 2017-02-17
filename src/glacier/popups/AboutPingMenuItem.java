/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package glacier.popups;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.impl.ToolWindowImpl;
import glacier.toolwindow.PingToolWindowFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by shuifeng.lsf on 2/16/17.
 *
 * @author shuifeng.lsf
 * @date 2017/02/16
 */
public class AboutPingMenuItem extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        JBPopupFactory factory = JBPopupFactory.getInstance();

        JTextArea jTextArea = new JTextArea("I'm a Demo！");
        jTextArea.setBackground(Color.lightGray);
        JComponent myPanel = new JPanel();
        myPanel.add(jTextArea);

        factory.createComponentPopupBuilder(myPanel, null)
                .setMinSize(new Dimension(50, 50))
                .setResizable(false)
                .setMovable(false)
                .createPopup()
                .showInCenterOf(PlatformDataKeys.TOOL_WINDOW.getData(anActionEvent.getDataContext()).getComponent());
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
}
