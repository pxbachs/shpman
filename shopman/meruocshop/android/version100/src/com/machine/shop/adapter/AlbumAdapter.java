//package com.machine.shop.adapter;
//
//import java.io.File;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.RelativeLayout;
//
//import com.androidquery.AQuery;
//import com.machine.shop.R;
//
//public class AlbumAdapter extends BaseAdapter implements OnClickListener {
//	private Context context;
//	private LayoutInflater inflater;
//
//	private News mCurrentNews;
//	private NewsImage[] mNewsImages;
//
//	private View[] mView;
//	private View mCover;
//
//	public AlbumAdapter(Context context, News currentNews) {
//		this.context = context;
//		this.inflater = LayoutInflater.from(context);
//		this.mCurrentNews = currentNews;
//		this.mNewsImages = mCurrentNews.getImages();
//		mView = new View[mNewsImages.length];
//	}
//
//	@Override
//	public int getCount() {
//		return this.mNewsImages.length + 2;
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return this.mNewsImages[position-1];
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return this.mNewsImages[position - 1].getId();
//	}
//
//	@Override
//	public int getViewTypeCount() {
//		return 2;
//	}
//	@Override
//	public int getItemViewType(int position) {
//		if (position == 0 || position == this.mNewsImages.length + 1) return 0;
//		return 1;
//	}
//	
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		if (position == 0 || position == this.mNewsImages.length + 1) {
//			if (mCover == null) {
//				mCover = inflater.inflate(R.layout.cover_page, null);
//			}
//			return mCover;
//		}
//
//		int aCurrentPosition = position - 1;
//		
//		View view = null;
//		if (mView[aCurrentPosition] == null) {
//			mView[aCurrentPosition] = inflater.inflate(R.layout.layout_album, parent, false);
//		}
//
//		view = mView[aCurrentPosition];
//
//		// if(view == null) view = inflater.inflate(R.layout.layout_album,
//		// parent, false);
//
//		AQuery aq = new AQuery(view);
//
//		// aq.id(R.id.album_image_item).image(mNewsImages[position].getUrl());
//		if (mNewsImages[aCurrentPosition].isFullScreen()) {
//			aq.id(R.id.album_image_item).getImageView().setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
//			aq.id(R.id.album_image_item).getImageView().setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);
//		}
//
//		aq.id(R.id.album_image_item).image(mNewsImages[aCurrentPosition].getUrl(), false, true, 0, 0, null, 0);
//		aq.id(R.id.album_header_name).text("Ảnh " + (aCurrentPosition + 1) + "/" + mNewsImages.length);
//
//		aq.id(R.id.album_header_name).clicked(this);
//		aq.id(R.id.button_back).clicked(this);
//		aq.id(R.id.button_share).clicked(this);
//		aq.id(R.id.album_image_item).clicked(this);
//		return view;
//	}
//
//	AQuery aq = new AQuery(this.context);
//	private int mCurrentPosition;
//
//	public void setCurrentPosition(int position) {
//		mCurrentPosition = position - 1;
//		// Preload next image
//		if (mCurrentPosition + 1 < mNewsImages.length) {
//			if (aq.getCachedFile(mNewsImages[mCurrentPosition + 1].getUrl()) == null) {
//				aq.cache(mNewsImages[mCurrentPosition + 1].getUrl(), 0);
//			}
//		}
//	}
//
//	@Override
//	public void onClick(View v) {
//		if (v.getId() == R.id.button_back) {
//			((Activity) this.context).onBackPressed();
//		} else if (v.getId() == R.id.button_share) {
//			File file = aq.makeSharedFile(mNewsImages[mCurrentPosition].getUrl(), "tin59s_shared_image.jpg");
//
//			if (file != null) {
//				Intent intent = new Intent(Intent.ACTION_SEND);
//				intent.setType("image/jpeg");
//				intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
//				((Activity) this.context).startActivityForResult(Intent.createChooser(intent, this.context.getResources().getString(R.string.share_image)), Activity.RESULT_CANCELED);
//			}
//		} else if (v.getId() == R.id.album_header_name) {
//		} else if (v.getId() == R.id.album_image_item) {
//			// show zoom activity
//			Intent intent = new Intent(context, ImageZoomActivity.class);
//			intent.putExtra("current_url", "file://" + mNewsImages[mCurrentPosition].getCachedUrl());
//			context.startActivity(intent);
//		}
//	}
//
//	public void onDestroy() {
//		mView = null;
//		mCurrentNews = null;
//		mNewsImages = null;
//		inflater = null;
//		System.gc();
//	}
//
//}
