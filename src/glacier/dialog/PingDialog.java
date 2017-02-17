package glacier.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.Nullable;
import sun.net.util.IPAddressUtil;

import javax.swing.*;

/**
 * Created by shuifeng.lsf on 2/16/17.
 *
 * @author shuifeng.lsf
 * @date 2017/02/16
 */
public class PingDialog extends DialogWrapper
{
    private JTextField ipAddress;
    private JPanel mainPanel;

    public PingDialog(@Nullable Project project, boolean canBeParent)
    {
        // 调用父类构造函数
        super(project, canBeParent);
        // 调用`init()`，不要漏掉这一步，否则窗口渲染失败；
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel()
    {
        // 结合 UI Designer forms 使用
        return mainPanel;
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate()
    {
        if (!IPAddressUtil.isIPv4LiteralAddress(getIpAddress()) &&
                !IPAddressUtil.isIPv6LiteralAddress(getIpAddress()))
        {
            return new ValidationInfo("请输入正确的IP地址!", ipAddress);
        }
        return super.doValidate();
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent()
    {
        // 返回弹出窗口后，焦点component
        return ipAddress;
    }

    public String getIpAddress()
    {
        return ipAddress.getText().trim();
    }
}
