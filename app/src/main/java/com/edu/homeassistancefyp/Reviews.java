package com.edu.homeassistancefyp;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Reviews.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Reviews#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reviews extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    private OnFragmentInteractionListener mListener;

    public Reviews() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Reviews.
     */
    // TODO: Rename and change types and number of parameters
    public static Reviews newInstance(String param1, String param2) {
        Reviews fragment = new Reviews();
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
            mParam1 = getArguments().getString("ARG_PARAM1");
            mParam2 = getArguments().getString("ARG_PARAM2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_reviews,
                container, false);
        db d=new db();
        d.execute(mParam1);


        return view;
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
    class db extends AsyncTask<String,Void,String>
    {
        String loc;
        String result="";
        protected void onPostExecute(String result)
        {try {
            String[] separated = result.split(":");
            Log.e("log_tagqbtaon", separated[5]);
            TableLayout tv = (TableLayout) view.findViewById(R.id.tableLayout);
            tv.removeAllViewsInLayout();
            int flag = 1;
            for (int i = -1; i <= separated.length - 3; i++) {
                TableRow tr = new TableRow(getActivity());
                tr.setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.FILL_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                if (flag == 1) {
                    TextView b6 = new TextView(getActivity());
                    b6.setText("Customer");
                    b6.setPadding(20, 40, 0, 0);
                    b6.setTextColor(Color.BLUE);
                    b6.setTextSize(22);
                    tr.addView(b6);
                    TextView b19 = new TextView(getActivity());
                    b19.setPadding(20, 40, 0, 0);
                    b19.setTextSize(22);
                    b19.setText("Rating");
                    b19.setTextColor(Color.BLUE);
                    tr.addView(b19);

                    TextView b22 = new TextView(getActivity());
                    b22.setPadding(20, 40, 0, 0);
                    b22.setTextSize(22);
                    b22.setText("Comments");
                    b22.setTextColor(Color.BLUE);
                    tr.addView(b22);

                    tv.addView(tr);
                    final View vline = new View(getActivity());
                    vline.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 2));
                    vline.setBackgroundColor(Color.BLUE);
                    tv.addView(vline);
                    flag = 0;
                } else {

                    TextView b = new TextView(getActivity());
                    String stime = separated[i];
                    b.setText(stime);
                    b.setPadding(20,20,0,0);
                    b.setTextColor(Color.BLACK);
                    b.setTextSize(20);
                    tr.addView(b);
                    i++;
                    TextView b1 = new TextView(getActivity());
                    b1.setPadding(50, 20, 0, 0);
                    b1.setTextSize(20);
                    String stime1 = separated[i];
                    b1.setText(stime1);
                    b1.setTextColor(Color.BLACK);
                    tr.addView(b1);
                    i++;
                    TextView b2 = new TextView(getActivity());
                    b2.setPadding(40, 20, 0, 0);
                    b2.setTextSize(20);
                    String stime2 = separated[i];
                    b2.setText(stime2);
                    b2.setTextColor(Color.BLACK);
                    tr.addView(b2);
                    tv.addView(tr);
                    final View vline1 = new View(getActivity());
                    vline1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                    vline1.setBackgroundColor(Color.WHITE);
                    tv.addView(vline1);
                }
            }
        } catch (Exception e) {
            Log.e("log_tag", "Error parsing data reviews data" + e.toString());

        }}
        @Override
        protected String doInBackground(String... strings) {
            loc=strings[0];

            String connectionString = "http://192.168.10.6/FYPHomeASsitant/Reviews.php";

            try {
                URL url = new URL(connectionString);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(mParam1, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null){
                    result += line;
                }
                Log.e("log_tag", result);
                reader.close();
                ips.close();
                http.disconnect();
                return result;


            } catch (Exception e) {
                result = e.getMessage();
            }
            return result;
        }
    }
}
