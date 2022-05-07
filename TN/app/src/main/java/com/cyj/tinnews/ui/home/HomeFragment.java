package com.cyj.tinnews.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cyj.tinnews.databinding.FragmentHomeBinding;
import com.cyj.tinnews.model.Article;
import com.cyj.tinnews.repository.NewsRepository;
import com.cyj.tinnews.repository.NewsViewModelFactory;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.StackFrom;

import java.util.List;


public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private CardStackLayoutManager layoutManager;
    private List<Article> articles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardSwipeAdapter swipeAdapter = new CardSwipeAdapter();
        layoutManager = new CardStackLayoutManager(requireContext());
        layoutManager.setStackFrom(StackFrom.Top);
        binding.homeCardStackView.setLayoutManager(layoutManager);
        binding.homeCardStackView.setAdapter(swipeAdapter);


        NewsRepository repository = new NewsRepository();
        // viewModel = new HomeViewModel(repository);
        viewModel = new ViewModelProvider(this,
                new NewsViewModelFactory(repository)).get(HomeViewModel.class);
        viewModel.setCountryInput("us");
        viewModel
                .getTopHeadlines()
                .observe(
                        getViewLifecycleOwner(),
                        newsResponse -> {
                            if (newsResponse != null) {
//                                Log.d("HomeFragment", newsResponse.toString());
                                articles = newsResponse.articles;
                                swipeAdapter.setArticles(articles);
                            }
                        });
    }

}