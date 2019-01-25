package com.example.rh.module.tools.translate;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.rh.core.base.BaseDelegate;
import com.example.rh.module.tools.R;
import com.example.rh.module.tools.R2;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author RH
 * @date 2019/1/21
 */
public class TranslateDelegate extends BaseDelegate<TranslatePresenter> implements ITranslate.View {

    @BindView(R2.id.delegate_translate)
    AppCompatEditText editText;

    @BindView(R2.id.delegate_translated)
    AppCompatTextView textView;
    @BindView(R2.id.delegate_translate_source)
    AppCompatTextView source;
    @BindView(R2.id.delegate_translate_target)
    AppCompatTextView target;

    @BindView(R2.id.delegate_translate_relative)
    RelativeLayout relativeLayout;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @OnClick(R2.id.delegate_translate_btn)
    void onClick() {
        presenter.toTranslate(editText.getText().toString(), "", "");
    }

    @OnClick(R2.id.delegate_translate_clean)
    void onClickClean() {
        editText.setText("");
    }

    @OnClick(R2.id.delegate_translate_alternating)
    void onClickAlternating() {
        String s = source.getText().toString();
        source.setText(target.getText().toString());
        target.setText(s);
    }

    @Override
    protected TranslatePresenter setPresenter() {
        return new TranslatePresenter(compositeDisposable);
    }

    @Override
    protected Object setLayout() {
        return R.layout.delegate_translate;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    relativeLayout.setVisibility(View.VISIBLE);
                } else {
                    // 此处为失去焦点时的处理内容
                    if (relativeLayout != null) {
                        relativeLayout.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    public void onTranslatedText(String s) {
        textView.setText(s);
    }

    @Override
    public void showToast(String s) {

    }
}
