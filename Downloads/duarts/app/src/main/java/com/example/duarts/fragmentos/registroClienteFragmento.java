package com.example.duarts.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.util.Log;
import com.example.duarts.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link registroClienteFragmento.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link registroClienteFragmento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class registroClienteFragmento extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private final static int FCR = 1;
    private String mCM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;
    private OnFragmentInteractionListener mListener;

    public registroClienteFragmento() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment registroClienteFragmento.
     */
    // TODO: Rename and change types and number of parameters
    public static registroClienteFragmento newInstance(String param1, String param2) {
        registroClienteFragmento fragment = new registroClienteFragmento();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v;
        v = inflater.inflate(R.layout.fragment_registro_cliente_fragmento, container, false);
        WebView webView = (WebView)v.findViewById(R.id.webview);
        assert webView != null;
       /* webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){
            // Need to accept permissions to use the camera
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request.grant(request.getResources());
                }
            }
        }); */
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(0);
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT < 19) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webView.setWebViewClient(new  WebViewClient());

        webView.loadUrl("https://app.duarts.com.br/aplicativo/novo/cliente");

        webView.setWebChromeClient(new WebChromeClient() {

            //For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {

                mUM = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                registroClienteFragmento.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FCR);
            }

            // For Android 3.0+, above method not supported in some android 3+ versions, in such case we use this
            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {

                mUM = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                registroClienteFragmento.this.startActivityForResult(
                        Intent.createChooser(i, "File Browser"),
                        FCR);
            }

            //For Android 4.1+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {

                mUM = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                registroClienteFragmento.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), registroClienteFragmento.FCR);
            }

            //For Android 5.0+
            public boolean onShowFileChooser(
                    WebView webView, ValueCallback<Uri[]> filePathCallback,
                    WebChromeClient.FileChooserParams fileChooserParams) {

                if (mUMA != null) {
                    mUMA.onReceiveValue(null);
                }

                mUMA = filePathCallback;
                // Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("*/*");
//                Intent[] intentArray;
//
//                if (takePictureIntent != null) {
//                    intentArray = new Intent[]{takePictureIntent};
//                } else {
//                    intentArray = new Intent[0];
//                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                // chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                // chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooserIntent, FCR);

                return true;
            }
        });




        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
