package com.w8er.android.view;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;

public class ExpandableTextView extends android.support.v7.widget.AppCompatTextView implements View.OnClickListener {
    private static final int MAX_LINES = 4;
    private int currentMaxLines = Integer.MAX_VALUE;
    private Boolean expandable = false;
    private Boolean first = true;
    private String saveStr = "";

    public ExpandableTextView(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnClickListener(this);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        /* If text longer than MAX_LINES set DrawableBottom - I'm using '...' icon */
        post(new Runnable() {
            public void run() {
                if (first && getLineCount() > MAX_LINES) {
                    saveStr = getText().toString().trim();
                    setMaxLines(MAX_LINES);
                    expandable = true;
                    first = false;
                    addExpandable();
                }
            }
        });
    }

    public void addExpandable() {
        if (getText() == null) return;
        int lineEndIndex = getLayout().getLineEnd(MAX_LINES - 1);
        String showMore = "...<br><font color=Blue>Show more</font>";
        String text = saveStr.subSequence(0, lineEndIndex - showMore.length() - 5) + showMore;
        setText(Html.fromHtml(text));
    }


    @Override
    public void setMaxLines(int maxLines) {
        currentMaxLines = maxLines;
        super.setMaxLines(maxLines);
    }

    /* Custom method because standard getMaxLines() requires API > 16 */
    public int getMyMaxLines() {
        return currentMaxLines;
    }

    @Override
    public void onClick(View v) {

        /* Toggle between expanded collapsed states */
        if (getMyMaxLines() == Integer.MAX_VALUE) {
            setMaxLines(MAX_LINES);
            if (expandable) {
                addExpandable();
            }
        } else {
            setMaxLines(Integer.MAX_VALUE);
            if (expandable) {
                String showLess = "<br><font color=Blue>Show less</font>";
                setText(Html.fromHtml(saveStr + showLess));
            }
        }
    }
}
