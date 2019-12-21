package com.reactnativecommunity.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLContext;

/**
 * Created by fooksing on 30/10/17.
 * Work for Android below 4.x
 */

public class SSLPinningWebView extends WebView {
    public SSLPinningWebView( Context context )
    {
        super( context );
    }

    public SSLPinningWebView(Context context, AttributeSet attributes )
    {
        super( context, attributes );
    }

    /**
     * Specify to use client certificate for authentication.
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws IOException
     * @throws KeyManagementException
     * @throws UnrecoverableKeyException
     * @throws IllegalArgumentException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    public void useCertificate() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException, UnrecoverableKeyException, IllegalArgumentException, ClassNotFoundException, IllegalAccessException
    {
        KeyPinStore keystore = KeyPinStore.getInstance();
        this.setSslContext( keystore.getContext() );
    }


    protected void setSslContext( SSLContext sslContext ) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException
    {
        Class<?> httpsConnection = Class.forName( "android.net.http.HttpsConnection" );
        Field[] fieldlist = httpsConnection.getDeclaredFields();
        for( int i = 0; i < fieldlist.length; i++ )
        {
            Field field = fieldlist[i];
            if( field.getName().equals( "mSslSocketFactory" ) )
            {
                field.setAccessible( true );
                field.set( null, sslContext.getSocketFactory() );
            }
        }
    }


}
