package com.example.cartotypemap;

import android.content.Context;

import com.cartotype.Framework;
import com.cartotype.MapView;

public class MainView extends MapView
{
    private Framework m_framework;

    MainView(Context aContext, Framework aFramework)
    {
        super(aContext,aFramework);
        m_framework = aFramework;
    }

    public void onTap(double aX,double aY)
    {
        m_framework.setPerspective(!m_framework.getPerspective());
    }

}

