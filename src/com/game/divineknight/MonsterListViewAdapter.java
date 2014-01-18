package com.game.divineknight;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MonsterListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ImageLoader imageLoader;
	private List<MonsterListResources> MonsterList = null;
	private ArrayList<MonsterListResources> arraylist;
	private HeroResources heroResource;

	public MonsterListViewAdapter(Context context,
			List<MonsterListResources> MonsterList, HeroResources heroResource) {
		this.context = context;
		this.heroResource = heroResource;
		this.MonsterList = MonsterList;
		inflater = LayoutInflater.from(context);
		this.arraylist = new ArrayList<MonsterListResources>();
		this.arraylist.addAll(MonsterList);
		imageLoader = new ImageLoader(context);
	}

	public class ViewHolder {
		TextView TVLevel;
		TextView TVName;
		TextView TVGoldGranted;
		ImageView TVImage;
	}

	@Override
	public int getCount() {
		return MonsterList.size();
	}

	@Override
	public Object getItem(int position) {
		return MonsterList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.monster_list_item, null);
			// Locate the TextViews in listview_item.xml
			holder.TVLevel = (TextView) view.findViewById(R.id.TVMonsterListLevel);
			holder.TVName = (TextView) view.findViewById(R.id.TVMonsterListName);
			holder.TVGoldGranted = (TextView) view.findViewById(R.id.TVMonsterListGoldGranted);
			// Locate the ImageView in listview_item.xml
			holder.TVImage = (ImageView) view.findViewById(R.id.TVMonsterListImage);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.TVLevel.setText("level: " + (int)MonsterList.get(position).getLevel());
		holder.TVName.setText("Monster: " + MonsterList.get(position).getName());
		holder.TVGoldGranted.setText("Gold Granted: " + (int)MonsterList.get(position).getGoldReward());
		// Set the results into ImageView
		imageLoader.DisplayImage(MonsterList.get(position).getMonsterImage(),holder.TVImage);
		// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Send single item click data to SingleItemView Class
				Intent intent = new Intent(context, MonsterStats.class);
				intent.putExtra("monsterLevel",MonsterList.get(position).getLevel());
				intent.putExtra("monsterName",MonsterList.get(position).getName());
				intent.putExtra("goldGranted",MonsterList.get(position).getGoldReward());
				intent.putExtra("monsterImage",MonsterList.get(position).getMonsterImage());
				intent.putExtra("heroResource", heroResource);
				MonsterListResources MonsterObject = new MonsterListResources();
				MonsterObject = MonsterList.get(position);
				intent.putExtra("monsterObject", MonsterObject);
				intent.putExtra("monsterLore", MonsterList.get(position).getMonsterLore());
				context.startActivity(intent);
			}
		});
		return view;
	}

}
