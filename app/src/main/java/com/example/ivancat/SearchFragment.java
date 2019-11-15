package com.example.ivancat;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {
    private EditText input;
    private RecyclerView recyclerView;
    private ArrayList<Cat> list = new ArrayList<>();
    private CatAdapter adapter;
    private RequestQueue requestQueue;
    private View icon;
    private View view;
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_search,container,false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        input = view.findViewById(R.id.input);
        dialog = new ProgressDialog(getActivity());
        requestQueue = Volley.newRequestQueue(getActivity());
        recyclerView = view.findViewById(R.id.recycleview);
        icon = view.findViewById(R.id.icon);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new CatAdapter(getActivity(),list);
        recyclerView.setAdapter(adapter);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = input.getText().toString();
                search(string);
            }
        });
    }

    private void search(String str){
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.thecatapi.com/v1/breeds/search?q="+str,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Gson gson = new Gson();
                        Cat[] objectsArray = gson.fromJson(response, Cat[].class);
                        List<Cat> dataList = Arrays.asList(objectsArray);
                        list.clear();
                        for (int i = 0; i < dataList.size(); i++) {
                            list.add(dataList.get(i));
                        }
                        adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        });
        requestQueue.add(stringRequest);
    }
}
