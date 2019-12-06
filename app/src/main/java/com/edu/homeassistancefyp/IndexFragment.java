package com.edu.homeassistancefyp;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IndexFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IndexFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndexFragment extends Fragment implements LocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LocationManager locationManager;
    TextView loca;
    Location lastLocation;
    String address;
    private OnFragmentInteractionListener mListener;

    public IndexFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IndexFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IndexFragment newInstance(String param1, String param2) {
        IndexFragment fragment = new IndexFragment();
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
        View view =  inflater.inflate(R.layout.fragment_index,
                container, false);
        Button getLocationBtn = (Button) view.findViewById(R.id.button);
        loca=(TextView) view.findViewById(R.id.loc);
        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        ImageView Beauty=(ImageView) view.findViewById(R.id.imageView6);
        Beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getLocation();
                Intent sw=new Intent(getActivity(),new searchWorkers().getClass());
                sw.putExtra("email",mParam1);
                sw.putExtra("categorie","Beauty");
                sw.putExtra("location",address);
                sw.putExtra("name",mParam2);

                startActivity(sw);

            }
        });

        ImageView Home=(ImageView) view.findViewById(R.id.imageView7);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                Intent sw=new Intent(getActivity(),new searchWorkers().getClass());
                sw.putExtra("email",mParam1);
                sw.putExtra("categorie","Home Cleaning");
                sw.putExtra("location",address);
                sw.putExtra("name",mParam2);

                startActivity(sw);
            }
        });

        ImageView Doctors=(ImageView) view.findViewById(R.id.imageView8);
        Doctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                Intent sw=new Intent(getActivity(),new searchWorkers().getClass());
                sw.putExtra("email",mParam1);
                sw.putExtra("categorie","Doctors");
                sw.putExtra("location",address);
                sw.putExtra("name",mParam2);

                startActivity(sw);
            }
        });

        ImageView CarWash=(ImageView) view.findViewById(R.id.imageView9);
        CarWash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                Intent sw=new Intent(getActivity(),new searchWorkers().getClass());
                sw.putExtra("email",mParam1);
                sw.putExtra("categorie","Car Wash");
                sw.putExtra("location",address);
                sw.putExtra("name",mParam2);

                startActivity(sw);
            }
        });

        ImageView Tutors=(ImageView) view.findViewById(R.id.imageView10);
        Tutors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                Intent sw=new Intent(getActivity(),new searchWorkers().getClass());
                sw.putExtra("email",mParam1);
                sw.putExtra("categorie","Tutors");
                sw.putExtra("location",address);
                sw.putExtra("name",mParam2);

                startActivity(sw);
            }
        });

        ImageView Plumbers=(ImageView) view.findViewById(R.id.imageView11);
        Plumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                Intent sw=new Intent(getActivity(),new searchWorkers().getClass());
                sw.putExtra("email",mParam1);
                sw.putExtra("categorie","Plumbers");
                sw.putExtra("location",address);
                sw.putExtra("name",mParam2);

                startActivity(sw);
            }
        });

        ImageView Electrician=(ImageView) view.findViewById(R.id.imageView12);
        Electrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                Intent sw=new Intent(getActivity(),new searchWorkers().getClass());
                sw.putExtra("email",mParam1);
                sw.putExtra("categorie","Electrician");
                sw.putExtra("location",address);
                sw.putExtra("name",mParam2);

                startActivity(sw);
            }
        });

        ImageView Massage=(ImageView) view.findViewById(R.id.imageView13);
        Massage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                Intent sw=new Intent(getActivity(),new searchWorkers().getClass());
                sw.putExtra("email",mParam1);
                sw.putExtra("categorie","Massage");
                sw.putExtra("location",address);
                sw.putExtra("name",mParam2);

                startActivity(sw);
            }
        });

        ImageView Fitness=(ImageView) view.findViewById(R.id.imageView14);
        Fitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                Intent sw=new Intent(getActivity(),new searchWorkers().getClass());
                sw.putExtra("email",mParam1);
                sw.putExtra("categorie","Fitness");
                sw.putExtra("location",address);
                sw.putExtra("name",mParam2);

                startActivity(sw);
            }
        });

        ImageView Maid=(ImageView) view.findViewById(R.id.imageView15);
        Maid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                Intent sw=new Intent(getActivity(),new searchWorkers().getClass());
                sw.putExtra("email",mParam1);
                sw.putExtra("categorie","Maid");
                sw.putExtra("location",address);
                sw.putExtra("name",mParam2);

                startActivity(sw);
            }
        });

        ImageView Carpenter=(ImageView) view.findViewById(R.id.imageView16);
        Carpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                Intent sw=new Intent(getActivity(),new searchWorkers().getClass());
                sw.putExtra("email",mParam1);
                sw.putExtra("categorie","Carpenter");
                sw.putExtra("location",address);
                sw.putExtra("name",mParam2);

                startActivity(sw);
            }
        });

        ImageView Teacher=(ImageView) view.findViewById(R.id.imageView17);
        Teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                Intent sw=new Intent(getActivity(),new searchWorkers().getClass());
                sw.putExtra("email",mParam1);
                sw.putExtra("categorie","Teacher");
                sw.putExtra("location",address);
                sw.putExtra("name",mParam2);

                startActivity(sw);
            }
        });



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
            address=getAddress(latLng);
            loca.setText(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

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
