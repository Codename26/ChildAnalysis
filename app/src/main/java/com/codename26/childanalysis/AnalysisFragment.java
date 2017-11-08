package com.codename26.childanalysis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by Dell on 07.11.2017.
 */

public class AnalysisFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.analysis_layout, null);
        WebView webView = v.findViewById(R.id.webView);
        String summary = "<html><body>\n" +
                "<table><tbody><tr><td width=\"144\">Стадия</td>\n" +
                "<td width=\"222\">% выявления</td>\n" +
                "<td width=\"222\">5-летняя выживаемость</td>\n" +
                "</tr><tr><td width=\"144\">I</td>\n" +
                "<td width=\"222\">20-27</td>\n" +
                "<td width=\"222\">73-93</td>\n" +
                "</tr><tr><td width=\"144\">II</td>\n" +
                "<td width=\"222\">5-10</td>\n" +
                "<td width=\"222\">45-70</td>\n" +
                "</tr><tr><td width=\"144\">III</td>\n" +
                "<td width=\"222\">52-58</td>\n" +
                "<td width=\"222\">21-37</td>\n" +
                "</tr><tr><td width=\"144\">IV</td>\n" +
                "<td width=\"222\">1-17</td>\n" +
                "<td width=\"222\">11-25</td>\n" +
                "</tr></tbody></table>\n" +
                "<table><tbody><tr><td width=\"144\">Стадия</td>\n" +
                "<td width=\"222\">% выявления</td>\n" +
                "<td width=\"222\">5-летняя выживаемость</td>\n" +
                "</tr><tr><td width=\"144\">I</td>\n" +
                "<td width=\"222\">20-27</td>\n" +
                "<td width=\"222\">73-93</td>\n" +
                "</tr><tr><td width=\"144\">II</td>\n" +
                "<td width=\"222\">5-10</td>\n" +
                "<td width=\"222\">45-70</td>\n" +
                "</tr><tr><td width=\"144\">III</td>\n" +
                "<td width=\"222\">52-58</td>\n" +
                "<td width=\"222\">21-37</td>\n" +
                "</tr><tr><td width=\"144\">IV</td>\n" +
                "<td width=\"222\">1-17</td>\n" +
                "<td width=\"222\">11-25</td>\n" +
                "</tr></tbody></table>\n" +
                "<table><tbody><tr><td width=\"144\">Стадия</td>\n" +
                "<td width=\"222\">% выявления</td>\n" +
                "<td width=\"222\">5-летняя выживаемость</td>\n" +
                "</tr><tr><td width=\"144\">I</td>\n" +
                "<td width=\"222\">20-27</td>\n" +
                "<td width=\"222\">73-93</td>\n" +
                "</tr><tr><td width=\"144\">II</td>\n" +
                "<td width=\"222\">5-10</td>\n" +
                "<td width=\"222\">45-70</td>\n" +
                "</tr><tr><td width=\"144\">III</td>\n" +
                "<td width=\"222\">52-58</td>\n" +
                "<td width=\"222\">21-37</td>\n" +
                "</tr><tr><td width=\"144\">IV</td>\n" +
                "<td width=\"222\">1-17</td>\n" +
                "<td width=\"222\">11-25</td>\n" +
                "</tr></tbody></table>\n" +
                "<table><tbody><tr><td width=\"144\">Стадия</td>\n" +
                "<td width=\"222\">% выявления</td>\n" +
                "<td width=\"222\">5-летняя выживаемость</td>\n" +
                "</tr><tr><td width=\"144\">I</td>\n" +
                "<td width=\"222\">20-27</td>\n" +
                "<td width=\"222\">73-93</td>\n" +
                "</tr><tr><td width=\"144\">II</td>\n" +
                "<td width=\"222\">5-10</td>\n" +
                "<td width=\"222\">45-70</td>\n" +
                "</tr><tr><td width=\"144\">III</td>\n" +
                "<td width=\"222\">52-58</td>\n" +
                "<td width=\"222\">21-37</td>\n" +
                "</tr><tr><td width=\"144\">IV</td>\n" +
                "<td width=\"222\">1-17</td>\n" +
                "<td width=\"222\">11-25</td>\n" +
                "</tr></tbody></table>\n" +
                "<table><tbody><tr><td width=\"144\">Стадия</td>\n" +
                "<td width=\"222\">% выявления</td>\n" +
                "<td width=\"222\">5-летняя выживаемость</td>\n" +
                "</tr><tr><td width=\"144\">I</td>\n" +
                "<td width=\"222\">20-27</td>\n" +
                "<td width=\"222\">73-93</td>\n" +
                "</tr><tr><td width=\"144\">II</td>\n" +
                "<td width=\"222\">5-10</td>\n" +
                "<td width=\"222\">45-70</td>\n" +
                "</tr><tr><td width=\"144\">III</td>\n" +
                "<td width=\"222\">52-58</td>\n" +
                "<td width=\"222\">21-37</td>\n" +
                "</tr><tr><td width=\"144\">IV</td>\n" +
                "<td width=\"222\">1-17</td>\n" +
                "<td width=\"222\">11-25</td>\n" +
                "</tr></tbody></table>\n" +
                "<table><tbody><tr><td width=\"144\">Стадия</td>\n" +
                "<td width=\"222\">% выявления</td>\n" +
                "<td width=\"222\">5-летняя выживаемость</td>\n" +
                "</tr><tr><td width=\"144\">I</td>\n" +
                "<td width=\"222\">20-27</td>\n" +
                "<td width=\"222\">73-93</td>\n" +
                "</tr><tr><td width=\"144\">II</td>\n" +
                "<td width=\"222\">5-10</td>\n" +
                "<td width=\"222\">45-70</td>\n" +
                "</tr><tr><td width=\"144\">III</td>\n" +
                "<td width=\"222\">52-58</td>\n" +
                "<td width=\"222\">21-37</td>\n" +
                "</tr><tr><td width=\"144\">IV</td>\n" +
                "<td width=\"222\">1-17</td>\n" +
                "<td width=\"222\">11-25</td>\n" +
                "</tr></tbody></table>\n" +
                "</body></html>";
        //webView.loadData(summary, "text/html", null);
        webView.loadDataWithBaseURL(null, summary,"text/html", "UTF-8", null);
        return v;
    }
}
