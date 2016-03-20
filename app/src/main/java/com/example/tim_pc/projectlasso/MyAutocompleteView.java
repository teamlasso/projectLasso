package com.example.tim_pc.projectlasso;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * Created by tim on 3/20/16.
 */
public class MyAutocompleteView extends AutoCompleteTextView{
    public MyAutocompleteView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MyAutocompleteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MyAutocompleteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void performFiltering(final CharSequence text, final int keyCode) {
        String filterText = "";
        super.performFiltering(filterText, keyCode);
    }

    @Override
    protected void replaceText(final CharSequence text) {
        super.replaceText(text);
    }
}
