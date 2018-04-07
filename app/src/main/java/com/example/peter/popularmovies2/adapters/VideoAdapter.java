package com.example.peter.popularmovies2.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.peter.popularmovies2.R;
import com.example.peter.popularmovies2.model.Video;
import com.example.peter.popularmovies2.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

/**
 * This adapter provides a list of available video's
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoAdapterViewHolder> {

    private final Context mContext;
    private final ArrayList<Video> mVideos;
    final private VideoAdapterOnClickHandler mClickHandler;

    public VideoAdapter(Context context, VideoAdapterOnClickHandler listener) {
        mContext = context;
        mVideos = new ArrayList<>();
        mClickHandler = listener;
    }

    @NonNull
    @Override
    public VideoAdapter.VideoAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                                                  int viewType) {

        int layoutIdForListItem = R.layout.video_list_item;
        View view = LayoutInflater.from(mContext).inflate(layoutIdForListItem,
                viewGroup, false);
        view.setFocusable(true);

        return new VideoAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoAdapterViewHolder
                                         videoAdapterViewHolder, int position) {

        Video currentVideo = mVideos.get(position);
        String videoKey = currentVideo.getVideoKey();
        URL videoUrl = NetworkUtils.getYouTubeThumbnailUrl(videoKey);

        if (videoUrl == null) {
            videoAdapterViewHolder.videoImageThumbnail.setVisibility(View.GONE);
        } else {
            videoAdapterViewHolder.videoImageThumbnail.setVisibility(View.VISIBLE);

            Picasso.with(mContext)
                    .load(String.valueOf(videoUrl))
                    .into(videoAdapterViewHolder.videoImageThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    public void updateVideos(ArrayList<Video> videos) {
        if (videos != null && !videos.isEmpty()) {
            mVideos.clear();
            mVideos.addAll(videos);
        } else {
            mVideos.clear();
        }
        notifyDataSetChanged();
    }

    class VideoAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final ImageView videoImageThumbnail;

        VideoAdapterViewHolder(View itemView) {
            super(itemView);

            videoImageThumbnail = itemView.findViewById(R.id.movie_detail_poster_small_iv);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Video currentVideo = mVideos.get(clickedPosition);
            mClickHandler.onClick(currentVideo);
        }
    }

    public interface VideoAdapterOnClickHandler {
        void onClick(Video currentVideo);
    }
}
