/*
 * Copyright 2006-2008 Sxip Identity Corporation
 */

/*
 * Created on Mar 5, 2007
 */
package org.openid4java.util;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * This class handles all HTTPClient connections for the
 * org.openid4java packages.
 * <p/>
 *
 * @author Kevin
 */
public class HttpClientFactory
{
    /**
     * proxy properties for HTTPClient calls
     */
    private static ProxyProperties proxyProperties = null;

    public static ProxyProperties getProxyProperties()
    {
        return proxyProperties;
    }

    public static void setProxyProperties(ProxyProperties properties)
    {
        proxyProperties = properties;
    }

    public static HttpClient getInstance(int maxRedirects,
                                         Boolean allowCircularRedirects,
                                         int connTimeout, int socketTimeout,
                                         String cookiePolicy)
    {

        Logger logger = Logger.getLogger("OpenID4Java HttpClientFactory");
        logger.info("Preparing a TLS v1.2 only httpclient:");
        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom()
                    .useTLS()
                    .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        SSLConnectionSocketFactory sslConnectionSocketFactory =
                new SSLConnectionSocketFactory(
                        sslContext,
                        new String[] { "TLSv1.2" },
                        null,
                        null);


        HttpClient httpClient = HttpClientBuilder.create()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build();

        logger.info("TLS v1.2 only httpclient ready.");


        /*
        httpClient.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, true);
        httpClient.getParams().setIntParameter(ClientPNames.MAX_REDIRECTS, maxRedirects);
        httpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, allowCircularRedirects);
        httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeout);
        httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connTimeout);

        if (cookiePolicy != null)
            httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,cookiePolicy);

        if (proxyProperties != null)
        {

            final HttpHost proxy = new HttpHost(
                    proxyProperties.getProxyHostName(),
                    proxyProperties.getProxyPort(),
                    "http");

            httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }
*/
        logger.info("TLS v1.2 only httpclient ready.");

        return httpClient;

        /*
        DefaultHttpClient client = new ThreadSafeHttpClient();

        client.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, true);
        
        client.getParams().setIntParameter(
                ClientPNames.MAX_REDIRECTS, maxRedirects);
        
        client.getParams().setParameter(
        		ClientPNames.ALLOW_CIRCULAR_REDIRECTS, allowCircularRedirects);
        
        client.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeout);
        
        client.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connTimeout);
        
        if (cookiePolicy == null)
        {
        	client.removeRequestInterceptorByClass(RequestAddCookies.class);
        	client.removeResponseInterceptorByClass(ResponseProcessCookies.class);
        }
        else
        {
        	client.getParams().setParameter(
				            		ClientPNames.COOKIE_POLICY, 
				                    cookiePolicy);
        }

        if (proxyProperties != null)
        {
        	
        	 final HttpHost proxy = new HttpHost(
        			 		proxyProperties.getProxyHostName(), 
                		 	proxyProperties.getProxyPort(), 
                		 	"http");
        	 
        	 client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        	 
        	 AuthScope scope = new AuthScope(proxyProperties.getProxyHostName(),
        			 					proxyProperties.getProxyPort());
        			 					
         	 client.getCredentialsProvider().setCredentials(scope, 
         			 						new UsernamePasswordCredentials(
         			 							proxyProperties.getUserName(),
         			 							proxyProperties.getPassword()));
        	 
        }

        return client;
        */
    }
}

