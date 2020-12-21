package com.yz.appdemo.util;

import android.content.Context;
import android.content.DialogInterface;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.yz.appdemo.R;
import com.yz.appdemo.common.Constant;

public class DialogUtil {

    public static QMUITipDialog loading(Context context) {
        QMUITipDialog loading = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("请稍候...")
                .create(true, R.style.QMUI_Dialog);
        loading.show();
        return loading;
    }


    public static QMUIDialog showInputDialog(Context context) {
        QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(context)
                .setTitle("请输入")
                .setDefaultText(Constant.SERVER_IP);
        QMUIDialog dialog = builder.addAction("确定", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                String text = builder.getEditText().getText().toString();
                dialog.dismiss();
            }
        }).show();
        return dialog;
    }

    public static QMUIDialog showItemDialog(Context context) {
        String languageNames[] = new String[]{"中文", "English"};
        int checkedItem = 0;
        QMUIDialog dialog = new QMUIDialog.CheckableDialogBuilder(context)
                .setCheckedIndex(checkedItem)
                .addItems(languageNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
        return dialog;
    }
}
