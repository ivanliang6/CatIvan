package com.example.ivancat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Map;

public class FavoritesFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Cat> list = new ArrayList<>();
    private CatAdapter adapter;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        recyclerView = view.findViewById(R.id.recycler);
        sharedPreferences = getActivity().getSharedPreferences("local", Context.MODE_PRIVATE);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager ll = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(ll);
        adapter = new CatAdapter(getActivity(),list);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        Map<String,?> map=sharedPreferences.getAll();
        list.clear();
        for(Map.Entry<String,?> entry :map.entrySet()){
            Cat cat = new Gson().fromJson(entry.getValue().toString(),Cat.class);
            list.add(cat);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
