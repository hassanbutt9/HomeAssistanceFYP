package com.edu.homeassistancefyp;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkerFragmentIndex.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkerFragmentIndex#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkerFragmentIndex extends Fragment implements LocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public TextView loca;
    LocationManager locationManager;
    Button getLocationBtn;
    Location lastLocation;
    TextView PJ,UJ;
    private OnFragmentInteractionListener mListener;

    public WorkerFragmentIndex() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkerFragmentIndex.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkerFragmentIndex newInstance(String param1, String param2) {
        WorkerFragmentIndex fragment = new WorkerFragmentIndex();
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
    void getLocation() {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
        lastLocation=location;
        try {
            String address=getAddress(latLng);
            loca.setText(address);
            db d=new db();
            d.execute(address);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_worker_fragment_index,
                container, false);
        TextView name=(TextView) view.findViewById(R.id.textView8);
        name.setText(mParam2);
        loca=(TextView) view.findViewById(R.id.loc);
        PJ=(TextView) view.findViewById(R.id.PJ);
        UJ=(TextView) view.findViewById(R.id.UJ);
        getLocationBtn = (Button) view.findViewById(R.id.btn);
        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                checkJobs cj=new checkJobs();
                cj.execute(mParam1);
                checkUJobs uj=new checkUJobs();
                uj.execute(mParam1);
            }
        });

        final TextView status=(TextView) view.findViewById(R.id.textView14);
        Switch sw=(Switch) view.findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b) {
                    status.setText("Go Offline");
                    saveState ss=new saveState();
                    ss.execute("Online");
                }
                else
                { status.setText("Go Online");
                saveState ss=new saveState();
                ss.execute("Offline");}
            }
        });

        PJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pend=new Intent(getActivity(),new PendingJobs().getClass());
                pend.putExtra("email",mParam1);
                startActivity(pend);
            }
        });
        UJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UJ.getText().equals("1"))
                {
                    Intent i=new Intent(getActivity(),new ViewJob().getClass());
                    i.putExtra("email",mParam1);
                    i.putExtra("name",mParam2);
                    i.putExtra("user","worker");
                    startActivity(i);
                }
            }
        });

        return view;
    }
    private String getAddress(LatLng latLng) throws IOException {
        String myCity="";
        Geocoder geocoder=new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses=geocoder.getFromLocation(lastLocation.getLatitude(),lastLocation.getLongitude(),1);
        String address=addresses.get(0).getAddressLine(0);
        String City=addresses.get(0).getLocality();
        Log.d("mylog", "Address"+address.toString());

        return address;
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
    class db extends AsyncTask<String,Void,Void>
    {
        String loc;
        String result;
        @Override
        protected Void doInBackground(String... strings) {
            loc=strings[0];

            String connectionString = "http://192.168.10.6/FYPHomeASsitant/addLoc.php";

            try {
                URL url = new URL(connectionString);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("loc", "UTF-8")+"="+URLEncoder.encode(loc, "UTF-8")
                        +"&&"+URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(mParam1, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String line = "";
                while ((line = reader.readLine()) != null){
                    result += line;
                }
                reader.close();
                ips.close();
                http.disconnect();


            } catch (Exception e) {
                result = e.getMessage();
            }
            return null;
        }
    }
    class saveState extends AsyncTask<String,Void,Void>
    {

        String result;
        @Override
        protected Void doInBackground(String... strings) {
            String status=strings[0];

            String connectionString = "http://192.168.10.6/FYPHomeASsitant/Status.php";

            try {
                URL url = new URL(connectionString);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("status", "UTF-8")+"="+URLEncoder.encode(status, "UTF-8")
                        +"&&"+URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(mParam1, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String line = "";
                while ((line = reader.readLine()) != null){
                    result += line;
                }
                reader.close();
                ips.close();
                http.disconnect();


            } catch (Exception e) {
                result = e.getMessage();
            }
            return null;
        }
    }
    class checkJobs extends AsyncTask<String,Void,String>
    {

        String result="";
        @Override
        protected void onPostExecute(String message)
        {
            if(!message.equals(null)) {
                PJ.setText(message);
                PJ.setEnabled(true);

            }
        }
        @Override
        protected String doInBackground(String... strings) {
            String status=strings[0];

            String connectionString = "http://192.168.10.6/FYPHomeASsitant/PJ.php";

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
                String line = "";
                while ((line = reader.readLine()) != null){
                    result += line;
                }
                reader.close();
                ips.close();
                http.disconnect();
                return  result;

            } catch (Exception e) {
                result = e.getMessage();
            }
            return result;
        }
    }
    class checkUJobs extends AsyncTask<String,Void,String>
    {

        String result="";
        @Override
        protected void onPostExecute(String message)
        {
            if(!message.equals(null) || !message.equals("0")) {
                UJ.setText(message);


            }
            else
                PJ.setEnabled(true);
        }
        @Override
        protected String doInBackground(String... strings) {
            String status=strings[0];

            String connectionString = "http://192.168.10.6/FYPHomeASsitant/UJ.php";

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
                String line = "";
                while ((line = reader.readLine()) != null){
                    result += line;
                }
                reader.close();
                ips.close();
                http.disconnect();
                return  result;

            } catch (Exception e) {
                result = e.getMessage();
            }
            return result;
        }
    }
}
