package com.chivox.sdkdemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder.AudioSource;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.chivox.AIEngine;
import com.chivox.AiUtil;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	private Spinner sourceSpinner;  
	private Spinner coretypeSpinner;
	private Spinner resSpinner;
	private Spinner cloudserverSpinner;
    private ArrayAdapter<String> adapter; 
    
    EditText rankText = null;
    EditText refText = null; //workbench
    EditText useridText = null;
    EditText precisionText = null;
    
    Button initButton = null;
	Button recordButton = null;
    Button replayButton = null;
    Button deleteButton = null;

    ToggleButton realtimeToggleButton = null;
    
    TextView scoreText =null;
    TextView reRefText = null; //result
    TextView abstractText = null;
    TextView detailText = null;
    TextView audioUrlText = null;
    TextView urlText = null;
    TextView dbText = null;
    
    TextView realtimeText = null;
    TextView cloudserverText = null;
    
    
    private String realtime = null;
    private String cloudServer = null;
    private String rank = null;
    private String ref = null;
    private String userid = null;
    private String coretype = null;
    private String source = null;
    private String res = null;
    private String precision = null;
    private String appKey = null;
    private String secretKey = null;
    
    private RecordMyCount recordmc;
    private ReplayMyCount replaymc;

   
    
    private String native_res_path = "";
//    		"{"
////          + "\"en.sent.score\":{\"resDirPath\": \"%s/eval/bin/eng.snt.robust.splp.offline.0.9\"}"
////          + "\"en.sent.score\":{\"resDirPath\": \"%s/eval/bin/eng.snt.g4.offline.0.2\"}"
////          + ",\"en.sent.score\":{\"resDirPath\": \"%s/eval/bin/eng.snt.g4.mb.0.3\"}"
//          + "\"en.sent.score\":{\"resDirPath\": \"%s/eval/bin/eng.snt.g4.P3.0.6\"}"
////          + "\"en.sent.score\":{\"resDirPath\": \"%s/eval/bin/eng.snt.g4.P3.0.5\"}"
////          + ",\"en.sent.score\":{\"resDirPath\": \"%s/eval/bin/eng.snt.g4.P2.0.6\"}"
////          + ",\"en.sent.score\":{\"resDirPath\": \"%s/eval/bin/eng.snt.g4.P2.0.5\"}"
////          + ",\"en.sent.score\":{\"resDirPath\": \"%s/eval/bin/eng.snt.g4.P2.0.4\"}"
////          + ",\"en.sent.score\":{\"resDirPath\": \"%s/eval/bin/eng.snt.splp.offline.0.12\"}"
////          + ",\"en.sent.score\":{\"resDirPath\": \"%s/eval/bin/eng.snt.strap.offline.0.9\"}"          
//          + ",\"en.word.score\":{\"resDirPath\": \"%s/eval/bin/eng.wrd.g4.P3.0.4\"}"
////          + ",\"en.word.score\":{\"resDirPath\": \"%s/eval/bin/eng.wrd.g4.mb.0.3\"}"
////          + ",\"en.word.score\":{\"resDirPath\": \"%s/eval/bin/eng.wrd.strap.offline.1.7\"}"
////          + ",\"en.word.score\":{\"resDirPath\": \"%s/eval/bin/eng.wrd.splp.offline.1.8\"}"         
////          + ",\"cn.word.score\":{\"resDirPath\": \"%s/eval/bin/chn.wrd.gnr.splp.offline.0.4\"}"         
////          + ",\"cn.sent.score\":{\"resDirPath\": \"%s/eval/bin/chn.snt.splp.offline.0.2\"}"         
////          + ",\"en.sent.rec\":{\"resDirPath\": \"%s/eval/bin/eng.rec.g4.mb.0.5\"}"
////          + ",\"en.sent.rec\":{\"resDirPath\": \"%s/eval/bin/eng.rec.g4.offline.0.4\"}"
////          + ",\"en.sent.rec\":{\"resDirPath\": \"%s/eval/bin/eng.rec.g4.P2.0.6\"}"
////          + ",\"en.sent.rec\":{\"resDirPath\": \"%s/eval/bin/eng.rec.splp.offline.0.3\"}"          
////          + ",\"cn.sent.rec\":{\"resDirPath\": \"%s/eval/bin/chn.rec.splp.offline.0.3\"}"         
////          + ",\"en.sred.exam\":{\"resDirPath\": \"%s/exam/bin/eng.sred.exam.1.0\"}" 
////          + ",\"en.srtl.exam\":{\"resDirPath\": \"%s/exam/bin/eng.srtl.exam.1.0\"}" 
//          + ",\"en.pred.exam\":{\"resDirPath\": \"%s/exam/bin/eng.pred.aux.P3.V4.11\"}" 
//          + "}";
    
    private String native_res_path_simp = "";
//    		"{"
//            + "\"en.sent.score\":{\"resDirPath\": \"%s/eval/simp/eng.snt.plp.simp.mb.0.1\"}"
//            + ",\"en.word.score\":{\"resDirPath\": \"%s/eval/simp/eng.wrd.plp.simp.mb.0.1\"}"
//            + ",\"cn.word.score\":{\"resDirPath\": \"%s/eval/simp/chn.wrd.plp.simp.mb.0.1\"}"
//            + ",\"cn.sent.score\":{\"resDirPath\": \"%s/eval/simp/chn.snt.plp.simp.mb.0.1\"}"
//            + "}";

    private static long engine = 0;
    private JSONObject cfg = null;
    private JSONObject params = null;
    
    private int vadEnable = 0; //ƒ¨»œ≤ª∆Ù”√vad
	private static int frequency = 16000;
	private int bufferSize = 0;
	private int sampleBytes = 2;
	private int sampleRate = 16000;
	private int channel = 1;
	private AudioRecord recorder = null;
    private AudioTrack player = null;
	private byte[] buffer = null;
	private boolean isRecording = false;
	private boolean isPlaying =false;
	private Thread recordingThread = null;
	private Thread playingThread = null;
	private Thread sshThread = null;
	
	private boolean isAuto = false;
	
	//private Thread unzipThread = null;
	private RandomAccessFile raf = null;
	private String wavPath = null;
	private int rv;
	private String resourceDir = null;
	private JSONArray detailArray = null;
	
	private Hashtable<String, File> cloudProvisionFileMap = new Hashtable<String, File>();
	private Hashtable<String, File> nativeProvisionFileMap = new Hashtable<String, File>();
	private Hashtable<String, File> simpnativeProvisionFileMap = new Hashtable<String, File>();
	private Hashtable<String, String[]> cloudProvisionKeyMap = new Hashtable<String, String[]>();
	private Hashtable<String, String[]> nativeProvisionKeyMap = new Hashtable<String, String[]>();
	private Hashtable<String, String[]> simpnativeProvisionKeyMap = new Hashtable<String, String[]>();
	
	private Double[] wordScores = null;
	
	private File nativeProvisionFile = null;
	private File simpnativeProvisionFile = null;
	private File cloudProvisionFile = null;
		
	TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
       {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        	if(charSequence.toString().trim().length()==0){
                recordButton.setEnabled(false);
               } else {
            	   recordButton.setEnabled(true);
                }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };
	//callback
    private AIEngine.aiengine_callback callback = new AIEngine.aiengine_callback(){
        public int run(byte[] id, int type, byte[] data, int size) {
            if (type == AIEngine.AIENGINE_MESSAGE_TYPE_JSON) {
                final String result = new String(data, 0, size).trim();
                try {
                    final JSONObject json = new JSONObject(result);
                    final String detailResult = json.toString(4); 
                    final String abstractResult = getAbstractResult(json).toString(4);
                    
                    
                    runOnUiThread(new Runnable() {
                        public void run() {
                        	mViewPager.setCurrentItem(1);
                        	getWordScore(json);
                        	reRefText.setText(Html.fromHtml(setWordColor()));
                        	
                        	detailText.setText(detailText.getText().toString() + detailResult);
                        	abstractText.setText(abstractText.getText().toString() + abstractResult);
                        	recordmc.cancel();
							recordButton.setText("Record");
							recordButton.setBackgroundColor(android.graphics.Color.parseColor("#B4EEB4"));
							replayButton.setEnabled(true);
							dbText.setText("");
							recorderStop();
							
                        	if (json.toString().contains("audioUrl")){
                        	
                        	try {
                        			audioUrlText.setVisibility(View.VISIBLE);
                        			urlText.setVisibility(View.VISIBLE);
									audioUrlText.setText(json.getString("audioUrl")+".ogg");
									audioUrlText.setAutoLinkMask(Linkify.ALL); 
									audioUrlText.setMovementMethod(LinkMovementMethod.getInstance()); 
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                        	}else {
								audioUrlText.setVisibility(View.GONE);
								urlText.setVisibility(View.GONE);
                        	}
                        	

                        }
                    });
                    
                    
                    
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return 0;
        }
    };
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		

//		sshThread = new Thread(new Runnable() {
//			public void run() {
//					try {
//						ssh();
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//			}});
//		sshThread.start();
		init();
		
		bindEvents();
		return true;
		
	}
	
	//Record Button Countdown
	private class RecordMyCount extends CountDownTimer {     
		         public RecordMyCount(long millisInFuture, long countDownInterval) {     
		             super(millisInFuture, countDownInterval);     
		         }     
		         @Override     
		         public void onFinish() {   
		        	 recorderStop();
		         }     
		         @Override     
		         public void onTick(long millisUntilFinished) {
		        	 recordButton.setBackgroundColor(android.graphics.Color.parseColor("#F08080"));
		        	 recordButton.setText("Stop(" + millisUntilFinished / 1000 + "s)");     
		         }    
		     }  
	
	//Replay Button Countdown
	private class ReplayMyCount extends CountDownTimer {     
        public ReplayMyCount(long millisInFuture, long countDownInterval) {     
            super(millisInFuture, countDownInterval);     
        }     
        @Override     
        public void onFinish() {   
        	playerStop();
        }
        @Override     
        public void onTick(long millisUntilFinished) {
       	 replayButton.setBackgroundColor(android.graphics.Color.parseColor("#F08080"));
       	 replayButton.setText("Stop(" + millisUntilFinished / 1000 + "s)");     
        }    
    }
	
	//source dropdown list listener
	private class SourceSpinnerSelectedListener implements AdapterView.OnItemSelectedListener{  
		  
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {  
        	String[] items = null;
        	// TODO: 
        	String sourceSelectedValue = sourceSpinner.getSelectedItem().toString();
            if (sourceSelectedValue.equals("cloud")){
            	items = getResources().getStringArray(R.array.cloud_coretype);
            	cloudserverText.setVisibility(View.VISIBLE);
            	cloudserverSpinner.setVisibility(View.VISIBLE);
            } else if (sourceSelectedValue.equals("native")) {
            	items = getResources().getStringArray(R.array.native_coretype);
            	cloudserverText.setVisibility(View.GONE);
            	cloudserverSpinner.setVisibility(View.GONE);
            } else if (sourceSelectedValue.equals("native-simp")) {
            	items = getResources().getStringArray(R.array.nativesimp_coretype);
            	cloudserverText.setVisibility(View.GONE);
            	cloudserverSpinner.setVisibility(View.GONE);
            }
            adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,items);  
            coretypeSpinner.setAdapter(adapter);


          
        }  
  
        public void onNothingSelected(AdapterView<?> arg0) {  
        }  
    }
	
	//coretype dropdown list listener
	private class CoreTypeSpinnerSelectedListener implements AdapterView.OnItemSelectedListener{  
		  
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) { 
        	String sourceSelectedValue = sourceSpinner.getSelectedItem().toString();
        	String coreTypeSelectedValue = coretypeSpinner.getSelectedItem().toString();
        	String[] items = null;
        	String ref = null;
        	String tip = "Word score and phoneme score are already on, and the score follow rank";
        	// TODO: 
        	realtimeToggleButton.setChecked(false);
        	realtimeText.setVisibility(View.GONE);
        	realtimeToggleButton.setVisibility(View.GONE);
    		
        	if (sourceSelectedValue.equals("cloud")){
        		if (coreTypeSelectedValue.equals("en.word.score")) {
            		items = getResources().getStringArray(R.array.cloud_en_word_score);
            		ref = getResources().getString(R.string.enWordScore);

            	}
        		else if (coreTypeSelectedValue.equals("en.sent.score")) {
            		items = getResources().getStringArray(R.array.cloud_en_sent_score);
            		ref = getResources().getString(R.string.enSentScore);

            	}
        		else if (coreTypeSelectedValue.equals("en.word.child")){
        			items = getResources().getStringArray(R.array.cloud_en_word_child);
            		ref = getResources().getString(R.string.enWordChild);

            	}
        		else if (coreTypeSelectedValue.equals("en.sent.child")){
        			items = getResources().getStringArray(R.array.cloud_en_sent_child);
            		ref = getResources().getString(R.string.enSentChild);

            	}
        		else if (coreTypeSelectedValue.equals("en.pred.exam")) {
            		items = getResources().getStringArray(R.array.cloud_en_pred_exam);
            		ref = getResources().getString(R.string.enPredExam);
            		realtimeText.setVisibility(View.VISIBLE);
            		realtimeToggleButton.setVisibility(View.VISIBLE);
            		toastShow(tip);
            	}
        	}
        	else if (sourceSelectedValue.equals("native")) {
        		if (coreTypeSelectedValue.equals("en.word.score")) {
            		items = getResources().getStringArray(R.array.native_en_word_score);
            		ref = getResources().getString(R.string.enWordScore);

            	}
        		else if (coreTypeSelectedValue.equals("en.sent.score")) {
            		items = getResources().getStringArray(R.array.native_en_sent_score);
            		ref = getResources().getString(R.string.enSentScore);

            	}
        		else if (coreTypeSelectedValue.equals("cn.sent.score")) {
            		items = getResources().getStringArray(R.array.native_cn_sent_score);
            		ref = getResources().getString(R.string.cnSentScore);

            	}
        		else if (coreTypeSelectedValue.equals("cn.word.score")) {
            		items = getResources().getStringArray(R.array.native_cn_word_score);
            		ref = getResources().getString(R.string.cnWordScore);

            	}
        		else if (coreTypeSelectedValue.equals("en.sent.rec")) {
            		items = getResources().getStringArray(R.array.native_en_sent_rec);
            		ref = getResources().getString(R.string.enSentRec);

            	}
        		else if (coreTypeSelectedValue.equals("cn.sent.rec")) {
            		items = getResources().getStringArray(R.array.native_cn_sent_rec);
            		ref = getResources().getString(R.string.cnSentRec);

            	}
        		else if (coreTypeSelectedValue.equals("en.pred.exam")) {
            		items = getResources().getStringArray(R.array.native_en_pred_exam);
            		ref = getResources().getString(R.string.enPredExam);
            		realtimeText.setVisibility(View.VISIBLE);
            		realtimeToggleButton.setVisibility(View.VISIBLE);
            		toastShow(tip);
            	}
        		
        		
        	}
        	else if (sourceSelectedValue.equals("native-simp")) {
        		if (coreTypeSelectedValue.equals("en.word.score")) {
            		items = getResources().getStringArray(R.array.nativesimp_en_word_score);
            		ref = getResources().getString(R.string.enWordScore);

            	}
        		else if (coreTypeSelectedValue.equals("en.sent.score")) {
            		items = getResources().getStringArray(R.array.nativesimp_en_sent_score);
            		ref = getResources().getString(R.string.enSentScore);

            	}
        		else if (coreTypeSelectedValue.equals("cn.sent.score")){
            		items = getResources().getStringArray(R.array.nativesimp_cn_sent_score);
            		ref = getResources().getString(R.string.cnSentScore);

            	}
        		else if (coreTypeSelectedValue.equals("cn.word.score")){
            		items = getResources().getStringArray(R.array.nativesimp_cn_word_score);
            		ref = getResources().getString(R.string.cnWordScore);

            	}
        	}
            
            adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,items);  
            resSpinner.setAdapter(adapter);
            
            refText.setText(ref);

          
        }  
  
        public void onNothingSelected(AdapterView<?> arg0) {  
        }  
    }
	
	
	

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
//			Fragment fragment = new DummySectionFragment();
//			Bundle args = new Bundle();
//			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
//			fragment.setArguments(args);
//			return fragment;
			
			switch (position) {
	        case 0:
	            return new WorkbenchFragment();
	        case 1:
	            return new ResultFragment();
	        }
	 
	        return null;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
//			return 3;
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
	}
	

//	/**
//	 * A dummy fragment representing a section of the app, but that simply
//	 * displays dummy text.
//	 */
//	public static class DummySectionFragment extends Fragment {
//		/**
//		 * The fragment argument representing the section number for this
//		 * fragment.
//		 */
//		public static final String ARG_SECTION_NUMBER = "section_number";
//
//		public DummySectionFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
//					container, false);
//			TextView dummyTextView = (TextView) rootView
//					.findViewById(R.id.section_label);
//			dummyTextView.setText(Integer.toString(getArguments().getInt(
//					ARG_SECTION_NUMBER)));
//			return rootView;
//		}
//	}
	
	public static class ResultFragment extends Fragment {
		 
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
	         
	        return rootView;
	    }
	}
	
	public static class WorkbenchFragment extends Fragment {
		 
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.fragment_workbench, container, false);
	         
	        return rootView;
	    }
	}
	
	
	//recorder initiate
	private void recorderInit() {
		bufferSize = 16000;
		int minBufferSize = AudioRecord.getMinBufferSize(frequency, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
		if (bufferSize < minBufferSize) {
			bufferSize = minBufferSize;
		}
		
		buffer = new byte[bufferSize];

		recorder = new AudioRecord(AudioSource.DEFAULT,frequency,AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT,bufferSize);
	}
	
	//start to record
	private void recorderStart() {
		try {
			wavPath = AiUtil.getFilesDir(
		              getApplicationContext()).getPath()
		              + "/"+System.currentTimeMillis()+".pcm";
					
					try {
						File file = new File(wavPath);
					
						raf = new RandomAccessFile(file,"rw");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
		//start record

		recorder.startRecording();
		
		isRecording = true;

		recordingThread = new Thread(new Runnable() {
			public void run() {
				while (isRecording) {
					if (recorder.getRecordingState() == AudioRecord.RECORDSTATE_STOPPED) {
						recorder.stop(); 
						break;
					}
					try {
						int r = recorder.read(buffer, 0, buffer.length);
						
						getDecibel(r);
						
						rv = AIEngine.aiengine_feed(engine, buffer, buffer.length);
						if (rv!=0){
							rv = AIEngine.aiengine_stop(engine);
							
							runOnUiThread(new Runnable() {
	                            public void run() {
	                            	mViewPager.setCurrentItem(1);
	                            	detailText.setText("aiengine_feed failed");
	                            	recordmc.cancel();
	                            	replayButton.setEnabled(true);
	                            	recordButton.setText("Record");
	                            	recordButton.setBackgroundColor(android.graphics.Color.parseColor("#B4EEB4"));
	                            	audioUrlText.setVisibility(View.GONE);
									urlText.setVisibility(View.GONE);
	                            }
	                        });
						}
						
						raf.write(buffer);
						
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	        	
			}
	}, "AudioRecorder Thread");
	recordingThread.setPriority(Thread.MAX_PRIORITY);
	recordingThread.start();
	
  while (isRecording && recorder.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING)
      Thread.yield(); // wait till recorder started
	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//stop recording
	private void recorderStop() {
		if (isRecording) {
            recorder.stop();
//            recordmc.cancel();
            isRecording = false;
            
            try {
    			raf.close();
    			recordingThread.join();
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
	
		runOnUiThread(new Runnable() {
            public void run() {
            	recordButton.setText("Record");
            	recordButton.setBackgroundColor(android.graphics.Color.parseColor("#B4EEB4"));
            	replayButton.setEnabled(true);
            	deleteButton.setEnabled(true);
            	toastShow("Finish, Please wait for the result");
            }
        });
    	
    	
            //4)engine stop
            rv = AIEngine.aiengine_stop(engine);
            if(rv!=0){
            	runOnUiThread(new Runnable() {
            		public void run() {
                    	mViewPager.setCurrentItem(1);
                    	detailText.setText("aiengine_stop failed");
                    	replayButton.setEnabled(true);
            			recordButton.setText("Record");
                    	recordButton.setBackgroundColor(android.graphics.Color.parseColor("#B4EEB4"));
                    	audioUrlText.setVisibility(View.GONE);
						urlText.setVisibility(View.GONE);
                 }
            	});
            }
        }
	}
	
	//replay initiate
	private void playerInit() {
        player = new AudioTrack(AudioManager.STREAM_MUSIC, frequency, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, buffer.length, AudioTrack.MODE_STREAM);
	}
	
	//start to replay
	private void playerStart() {
		
		playingThread = new Thread(new Runnable() {
			public void run() {
				if (wavPath != null){
				player.play();
				isPlaying = true;
				try {
					raf = new RandomAccessFile(wavPath, "r");
					while(isPlaying) {
						int size = raf.read(buffer, 0, buffer.length);
                        if (size == -1) {
                            break;
                        }
                        player.write(buffer, 0, size);
					}
					player.flush();
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
	}, "AudioTrack Thread");
		playingThread.setPriority(Thread.MAX_PRIORITY);
		playingThread.start();
	}
	
	//stop replaying
	private void playerStop() {			
			if (isPlaying) {
				player.stop();
				isPlaying = false;
				try {
					raf.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				runOnUiThread(new Runnable() {
	        		public void run() {
	        			replayButton.setText("Replay");
	        			replayButton.setBackgroundColor(android.graphics.Color.parseColor("#FFFF00"));
	        			recordButton.setEnabled(true);
	        			deleteButton.setEnabled(true);
	        			toastShow("Finish");
	        		}
	        	});
			}						
	}
	
	//get serialnumber and deviceid
	private String getSerialNumber(String cfg)
    {
        byte buf[] = new byte[64];
        JSONObject cfg_json = null;
        AIEngine.aiengine_get_device_id(buf, MainActivity.this);
        try {
            cfg_json = new JSONObject(cfg);
//            cfg_json.put("deviceId", new String(buf).trim());
            cfg_json.put("appKey", "xxxxxxxxxxxx");
            cfg_json.put("secretKey", "xxxxxxxxxxxxxxxxxxxxx");
            cfg_json.put("userId", "xxx");
            
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte cfg_b[] = Arrays.copyOf(cfg_json.toString().getBytes(), 1024);
        int ret = AIEngine.aiengine_opt(0L, 6, cfg_b, 1024);
        if (ret > 0) {
            return new String(cfg_b, 0, ret);
        } else {
            return new String(cfg_b);
        }
    }
	
	private void setParam(){
		runOnUiThread(new Runnable() {
            public void run() {
                
                precision = precisionText.getText().toString();
                rank = rankText.getText().toString();
                ref = refText.getText().toString();
                if (source.equals("cloud")){
                	coretype = coretypeSpinner.getSelectedItem().toString();
                	res = resSpinner.getSelectedItem().toString();
                }
                userid = useridText.getText().toString();
//                coretype = coretypeSpinner.getSelectedItem().toString();
//                source = sourceSpinner.getSelectedItem().toString();
//                res = resSpinner.getSelectedItem().toString();
//                cloudServer = cloudserverSpinner.getSelectedItem().toString();
                realtime = realtimeToggleButton.getText().toString();
                
            }
        });

		params = new JSONObject();
		
		//param
        try {       	
        	if (source.equals("cloud")) {
	        	params.put("audio", new JSONObject(
	                    "{\"audioType\": \"wav\",\"sampleBytes\": " + sampleBytes + ",\"sampleRate\": " + sampleRate + ",\"channel\": " + channel + ",\"compress\": \"speex\"}"));
	        	
	            params.put("app", new JSONObject(
	                    "{\"userId\":\""+userid+"\"}"));
	            
	            params.put("coreProvideType", "cloud");
	
	        	JSONObject request = new JSONObject();
	        	
	        	request.put("rank",  Integer.parseInt(rank));
	        	request.put("coreType", coretype);
	        	if (!res.equals("")){
	        		request.put("res", res);        	
	        	}
	        	request.put("precision",Double.parseDouble(precision));
	        	request.put("attachAudioUrl",1);
	            
	            
	            if (coretypeSpinner.getSelectedItem().toString().contains("exam")){
	            	
	            	if (realtime.equals("OFF")){
	            		request.put("client_params",new JSONObject("{\"ext_subitem_rank4\": 0, \"ext_word_details\": 1,\"ext_phn_details\":1}"));
	            	}
	            	else if (realtime.equals("ON")){
	            		request.put("client_params",new JSONObject("{\"ext_subitem_rank4\": 0, \"ext_word_details\": 1,\"ext_phn_details\":1, \"ext_cur_snt\":1}"));
	            	}
	            	request.put("refText",new JSONObject("{\"lm\": \""+ref+"\"}"));	
	            }else {
	            	request.put("refText",ref);
	            }
	            
	            //request.put("result", new JSONObject("{\"use_details\":1,\"details\":{\"oovscore\":0}}"));
	            
	            params.put("request", request);
            
        	}else { //native & native-simp
        		params.put("app", new JSONObject(
                		"{\"userId\":\""+userid+"\"}"));
        		
        		params.put("coreProvideType", "native");
        		           	

        		params.put("audio", new JSONObject(
        				"{\"audioType\": \"wav\",\"sampleBytes\": " + sampleBytes + ",\"sampleRate\": " + sampleRate + ",\"channel\": " + channel + ",\"compress\": \"speex\"}"));
            	
        		
                JSONObject request = new JSONObject();
            	
            	request.put("rank",  Integer.parseInt(rank));
            	request.put("coreType", coretype);
//            	request.put("res", res);
            	
            	
            	
            	if (coretypeSpinner.getSelectedItem().toString().contains("exam")){
            		if (realtime.equals("OFF")){
	            		request.put("client_params",new JSONObject("{\"ext_subitem_rank4\": 0, \"ext_word_details\": 1,\"ext_phn_details\":1}"));
	            	}
	            	else if (realtime.equals("ON")){
	            		request.put("client_params",new JSONObject("{\"ext_subitem_rank4\": 0, \"ext_word_details\": 1,\"ext_phn_details\":1, \"ext_cur_snt\":1}"));
	            	}
                	request.put("refText",new JSONObject("{\"lm\": \""+ref+"\"}"));	
//                	request.put("qid", "");
                }else {
                	request.put("refText",ref);
                }
            	
            	//request.put("relaxation_factor", 0);
            	
            	request.put("precision",Double.parseDouble(precision));
            	//request.put("result", new JSONObject("{\"use_details\":1,\"details\":{\"oovscore\":0}}"));
                params.put("request", request);
                                
        	}
        	System.out.println(params);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	//cfg, param config
	private void setConfig() {
		runOnUiThread(new Runnable() {
            public void run() {
                
//                precision = precisionText.getText().toString();
//                rank = rankText.getText().toString();
//                ref = refText.getText().toString();
//                userid = useridText.getText().toString();
                coretype = coretypeSpinner.getSelectedItem().toString();
                source = sourceSpinner.getSelectedItem().toString();
                res = resSpinner.getSelectedItem().toString();
                cloudServer = cloudserverSpinner.getSelectedItem().toString();
//                realtime = realtimeToggleButton.getText().toString();
                
            }
        });

		cfg = new JSONObject();
//		params = new JSONObject();
		

	    		
		//cfg
		try {
		if (source.equals("cloud")) {
	        appKey = cloudProvisionKeyMap.get(cloudServer)[0];
	        secretKey = cloudProvisionKeyMap.get(cloudServer)[1];
	        cloudProvisionFile = cloudProvisionFileMap.get(cloudServer);
	        
	        
	        
			cfg.put("appKey", appKey);
            
            cfg.put("secretKey", secretKey);
            
            cfg.put("prof", new JSONObject("{\"enable\": 1, \"output\": \"/sdcard/sdkdemo.log\"}"));
            
            // cloud
            cfg.put("cloud", new JSONObject("{\"server\": \"" + cloudServer + "\",\"serverList\":\"\"}"));
			
            cfg.put("provision", cloudProvisionFile.getAbsolutePath());

		}else if (source.equals("native-simp")){
			native_res_path_simp = "{"
				          + "\""+ coretype +"\":{\"resDirPath\": \"%s/eval/simp/"+res+"\"}"          
				          + "}";
	        appKey = simpnativeProvisionKeyMap.get("native_simp")[0];
	        secretKey = simpnativeProvisionKeyMap.get("native_simp")[1];
	        simpnativeProvisionFile = simpnativeProvisionFileMap.get("native_simp");
			cfg.put("appKey", appKey);
            
            cfg.put("secretKey", secretKey);
            
            cfg.put("prof", new JSONObject("{\"enable\": 1, \"output\": \"/sdcard/sdkdemo.log\"}"));

            //native simp
            String res_path = new String();

            res_path = String.format(native_res_path_simp, resourceDir, resourceDir
                    , resourceDir, resourceDir, resourceDir);
            cfg.put("native", new JSONObject(res_path)); // native_res_path2

          cfg.put("provision", simpnativeProvisionFile.getAbsolutePath());
		}else if (source.equals("native")) {
			//TODO:
			if (coretype.contains("exam")){
				native_res_path = "{"
				          + "\""+ coretype +"\":{\"resDirPath\": \"%s/exam/bin/"+res+"\"}" 
				          + "}";
			}else{
				native_res_path = "{"
				          + "\""+ coretype +"\":{\"resDirPath\": \"%s/eval/bin/"+res+"\"}"          
				          + "}";
			}
			appKey = nativeProvisionKeyMap.get("native")[0];
	        secretKey = nativeProvisionKeyMap.get("native")[1];
	        nativeProvisionFile = nativeProvisionFileMap.get("native");
	        
			cfg.put("appKey", appKey);
            
            cfg.put("secretKey", secretKey);
            
            cfg.put("prof", new JSONObject("{\"enable\": 1, \"output\": \"/sdcard/sdkdemo.log\"}"));

            //native
            String res_path = new String();
           res_path = String.format(native_res_path, resourceDir, resourceDir
                    , resourceDir, resourceDir, resourceDir);
            
            cfg.put("native", new JSONObject(res_path)); // native_res_path2

            cfg.put("provision", nativeProvisionFile.getAbsolutePath());
		}
		
        String serialNum = getSerialNumber(cfg.toString());
        System.out.println(cfg);
//        cfg.put("serialNumber", serialNum);
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		
	}
	
	//get volume
	private void getDecibel(int r){
		if (isRecording == true){
		long v = 0;  
 
        for (int i = 0; i < buffer.length; i++) {  
            v += buffer[i] * buffer[i];  
        }  
  
        double mean = v / (double) r;  
        final double volume = 10 * Math.log10(mean);
		
        runOnUiThread(new Runnable() {
            public void run() {
		dbText.setText(Double.toString(volume));
            }
        });
		}
	}
	
	//get relationships between provision, appkey and secretKey
	private void getMap(String[] stringArray, String flag){
		for (int i=0; i < stringArray.length ; i+=3 ){
			if (flag.equals("cloud")){
				cloudProvisionFileMap.put(stringArray[i], getProvision("aiengine_cloud_"+getIP(stringArray[i])+".provision"));
				String[] keys = {stringArray[i+1],stringArray[i+2]};
				cloudProvisionKeyMap.put(stringArray[i], keys);
			}else if (flag.equals("native")){
				nativeProvisionFileMap.put(stringArray[i], getProvision("aiengine_"+stringArray[i]+".provision"));
				String[] keys = {stringArray[i+1],stringArray[i+2]};
				nativeProvisionKeyMap.put(stringArray[i], keys);
			}else if (flag.equals("native_simp")){
				simpnativeProvisionFileMap.put(stringArray[i], getProvision("aiengine_"+stringArray[i]+".provision"));
				String[] keys = {stringArray[i+1],stringArray[i+2]};
				simpnativeProvisionKeyMap.put(stringArray[i], keys);
			}

		}
	}
	
	//get provision
	private File getProvision(String filename){
		InputStream is = null;
		File provision = null;
		try {
			is = getAssets().open(filename);

	        
			provision = new File(
              AiUtil.externalFilesDir(MainActivity.this),
              filename);
			AiUtil.writeToFile(provision, is);
			is.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return provision;
	}
	
	//get ip or domain for cloud service
	private String getIP(String str) {
		String result = null;
		Pattern p = Pattern.compile("ws://(.*?):");
		Matcher m = p.matcher(str);
		 while(m.find()) {
		       result = m.group(1);
		 }
		 return result;
	}
	
	
	//get record duration
	private int getRecordDuration(String text){
		if (coretype.contains("en.") && vadEnable == 0){
			int wordNum = text.trim().split("\\W+").length;
			return (int)((5.0F + (float)wordNum * 0.6F) * 1000F);
		}else if(coretype.contains("en.") && vadEnable == 1){
			int wordNum = text.trim().split("\\W+").length;
			return (int)((5.0F + (float)wordNum * 0.3F) * 1000F);
		}else{
			return 20000;
		}
	}
	
	//get score of each word
	private void getWordScore(JSONObject json){
		String[] words = ref.split(" ");
		if (coretype.contains("sent")){
			try {
				detailArray = json.getJSONObject("result").getJSONArray("details");
				if (detailArray.length() == words.length){
					wordScores = new Double[words.length];
					for (int i = 0;i < detailArray.length(); i++) {
	
					    JSONObject object = (JSONObject) detailArray.get(i);
					    wordScores[i] = Double.parseDouble(object.getString("score"));
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		else if (coretype.contains("word")){
			try {
				detailArray = json.getJSONObject("result").getJSONArray("details");
				if (detailArray.length() == words.length){
					wordScores = new Double[words.length];
					for (int i = 0;i < detailArray.length(); i++) {
						JSONObject object = (JSONObject) detailArray.get(i);
						wordScores[i] = Double.parseDouble(object.getString("score"));
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (coretype.contains("exam") && realtime.equals("OFF")){
			try {
				detailArray = json.getJSONObject("result").getJSONArray("details");
				if (detailArray.length() == words.length){
					int k =0;
					wordScores = new Double[words.length];
					for (int i = 0;i < detailArray.length(); i++) {
						JSONObject detailObject = (JSONObject) detailArray.get(i);
					    JSONArray wordsArray = detailObject.getJSONArray("words");
					    for (int j =0; j< wordsArray.length();j++){
					    	JSONObject wordsObject = (JSONObject) wordsArray.get(j);
					    	wordScores[k] = Double.parseDouble(wordsObject.getString("score"));
					    	k++;
					    }
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		return result;
		
	}
	
	//set color for each word according to score
	private String setWordColor(){
		String result = "";
		String[] words = ref.split(" ");		
			scoreText.setText(Html.fromHtml("<br /><font color='#76EEC6'>Excellent</font> <font color='#63B8FF'>Good</font> <font color='#A1A1A1'>Moderate</font> <font color='#FF0000'>Poor</font>"));
			if ((wordScores == null) ||( wordScores.length == 0) || (detailArray.length() != words.length)) {
				return ref;
			}else {
				for (int i =0; i< words.length ;i++) {
					if (wordScores[i] != null && rank.equals("100") && coretype.contains("en.") && realtime.equals("OFF")){
					    	  if (wordScores[i] < (double)70) {
					    		  result = result + "<font color='#FF0000'>" + words[i] + " </font>";
					    	  }
					    	  else if (wordScores[i] < (double)80) {
					    		  result = result + "<font color='#A1A1A1'>" + words[i] + " </font>";
					    	  }
					    	  else if (wordScores[i] < (double)90) {
					    		  result = result + "<font color='#63B8FF'>" + words[i] + " </font>";
					    	  }
					    	  else if (wordScores[i] <= (double)100) {
					    		  result = result + "<font color='#76EEC6'>" + words[i] + " </font>";
					    	  }
				    }else{
				    		  result = result + words[i] + " ";
				    	  }
					
						if ((i+1) % 8 == 0) {
							result = result + "<br/>" ;
						}
				}
		      
		      return result;
			}
	}
	
	//get simple result
	private JSONObject getAbstractResult(JSONObject json){
		
		JSONObject result = new JSONObject();
		if (source.equals("native-simp")){
			try {
				result.put("overall", json.getJSONObject("result").getString("overall"));
				result.put("res", json.getJSONObject("result").getString("res"));
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (source.equals("native")){
			try {					
					result.put("info", json.getJSONObject("result").getJSONObject("info"));
					result.put("res", json.getJSONObject("result").getString("res"));
					result.put("overall", json.getJSONObject("result").getString("overall"));
					result.put("pron", json.getJSONObject("result").getString("pron"));
					if (coretype.contains("pred")){
						result.put("fluency",json.getJSONObject("result").getString("fluency"));
					}else{
						result.put("fluency",json.getJSONObject("result").getJSONObject("fluency"));
					}
					
					result.put("integrity", json.getJSONObject("result").getString("integrity"));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (source.equals("cloud")){
			try {
			result.put("info", json.getJSONObject("result").getJSONObject("info"));
			result.put("res", json.getJSONObject("result").getString("res"));
			result.put("overall", json.getJSONObject("result").getString("overall"));
			result.put("pron", json.getJSONObject("result").getString("pron"));
			if (coretype.contains("pred")){
				result.put("fluency",json.getJSONObject("result").getString("fluency"));
			}else{
				result.put("fluency",json.getJSONObject("result").getJSONObject("fluency"));
			}
			result.put("integrity", json.getJSONObject("result").getString("integrity"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		return result;
		
	}
	
	//toast
	private void toastShow(String msg){
		Toast toast = Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT);  
        toast.setGravity(Gravity.CENTER, 0, 0);  
        toast.show(); 
        
	}
	
	//initiate
	private void init() {
        sourceSpinner = (Spinner) this.findViewById(R.id.spinner1);  
        
        String[] items = getResources().getStringArray(R.array.source);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items);
        sourceSpinner.setAdapter(adapter);
        sourceSpinner.setOnItemSelectedListener(new SourceSpinnerSelectedListener()); 
        
		
        coretypeSpinner = (Spinner) this.findViewById(R.id.spinner2);
        coretypeSpinner.setOnItemSelectedListener(new CoreTypeSpinnerSelectedListener()); 

        realtimeText = (TextView) this.findViewById(R.id.realtimetextView);
        realtimeToggleButton = (ToggleButton) this.findViewById(R.id.toggleButton1);
        
        resSpinner = (Spinner) this.findViewById(R.id.spinner3);
		
        cloudserverText = (TextView) this.findViewById(R.id.cloudservertextView);
        cloudserverSpinner = (Spinner) this.findViewById(R.id.spinner4);
        String[] m = getResources().getStringArray(R.array.cloudserver);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);
        cloudserverSpinner.setAdapter(adapter);
        
        initButton = (Button) this.findViewById(R.id.button0);
 		recordButton = (Button) this.findViewById(R.id.button1);
		replayButton = (Button) this.findViewById(R.id.button2);
		deleteButton = (Button) this.findViewById(R.id.button3);
    	initButton.setEnabled(true);
    	recordButton.setEnabled(false);
    	replayButton.setEnabled(false);
    	deleteButton.setEnabled(false);
		
    	
    	
    	
		rankText = (EditText) this.findViewById(R.id.rankeditText);
		useridText = (EditText) this.findViewById(R.id.userideditText);
		precisionText = (EditText) this.findViewById(R.id.precisioneditText);
		refText = (EditText) this.findViewById(R.id.reftexteditText);
		
		rankText.setEnabled(false);
		useridText.setEnabled(false);
		precisionText.setEnabled(false);
		refText.setEnabled(false);
		
		precisionText.clearFocus();
		
		scoreText =  (TextView) this.findViewById(R.id.scoretextView);
		reRefText = (TextView) this.findViewById(R.id.rereftextView);
		
		detailText = (TextView) this.findViewById(R.id.detailtextView);
		abstractText = (TextView) this.findViewById(R.id.abstracttextView);
		audioUrlText = (TextView) this.findViewById(R.id.audioUrltextView);
		urlText = (TextView) this.findViewById(R.id.urltextView);
		dbText = (TextView) this.findViewById(R.id.dbtextView);
		
		resourceDir = new String(AiUtil.unzipFile(MainActivity.this, "resources.zip").toString());	        	
		
        
		getMap(getResources().getStringArray(R.array.cloudprovision),"cloud");
		getMap(getResources().getStringArray(R.array.nativeprovision),"native");
		getMap(getResources().getStringArray(R.array.nativesimpprovision),"native_simp");

		
		recorderInit();
        playerInit();
    	
	}
	
	//ssh
	public void ssh() throws Exception {  
	    JSch jsch = new JSch();  
	  
	    // connect session  
	    Session session = jsch.getSession("root", "10.0.200.164", 5837);  
	    session.setPassword("qtest88!!");  
	    session.setConfig("StrictHostKeyChecking", "no");  
	    session.connect();  
	  
	    
	    if(session.isConnected()){
            System.out.println(this.getClass().getSimpleName() + " CONNECTED");
//            System.out.println(this.getClass().getSimpleName() + " YOO " + jsch.getIdentityRepository().getName()+" "+session.getClientVersion() + " " + session.isConnected());
        }else{
            System.out.println(this.getClass().getSimpleName() + " NOT CONNECTED");
        }
	    
	    // exec command remotely  
	    String command = "touch /root/sshtest.txt";  
	    ChannelExec channel = (ChannelExec) session.openChannel("exec");  
	    channel.setCommand(command);  
	    channel.connect();  
	  
	    
	    
	    
	    // get stdout  
	    InputStream in = channel.getInputStream();  
	    byte[] tmp = new byte[1024];  
	    while (true) {  
	        while (in.available() > 0) {  
	            int i = in.read(tmp, 0, 1024);  
	            if (i < 0)  
	                break;  
	            System.out.print(new String(tmp, 0, i));  
	        }  
	        if (channel.isClosed()) {  
	            System.out.println("exit-status: " + channel.getExitStatus());  
	            break;  
	        }  
	        try {  
	            Thread.sleep(1000);  
	        } catch (Exception ee) {  
	        }  
	    }  
	    channel.disconnect();  
	    session.disconnect();  
	}  
	
	
    
    private void autoTest(){
        byte[] id = new byte[64];
        byte[] data = new byte[2048];
        int rsize;
        int offSet = 44;
//        recordButton.;
        recordButton.setVisibility(View.INVISIBLE);
        replayButton.setVisibility(View.INVISIBLE);
        int rv = AIEngine.aiengine_start(engine, params.toString(), id,
                callback, MainActivity.this);
        if(rv!=0){
        	runOnUiThread(new Runnable() {
                public void run() {
                	mViewPager.setCurrentItem(1);
                	detailText.setText("aiengine_start failed");
                	recordmc.cancel();
                	replayButton.setEnabled(true);
                	deleteButton.setEnabled(true);
                	recordButton.setText("Record");
                	recordButton.setBackgroundColor(android.graphics.Color.parseColor("#B4EEB4"));
                	audioUrlText.setVisibility(View.GONE);
					urlText.setVisibility(View.GONE);
                }
            });
        	rv = AIEngine.aiengine_stop(engine);
            return;
        }
        String wavPath = "";
        if(coretype.contains("pred")){
        wavPath = AiUtil.getFilesDir(
                getApplicationContext()).getPath() + "/resources/ep2.wav";
        }else{
            wavPath = AiUtil.getFilesDir(
                    getApplicationContext()).getPath() + "/resources/es2.wav";
        }
        RandomAccessFile file = null;
        if (wavPath != null) {
            try {
                File f = new File(wavPath);
                file = new RandomAccessFile(f, "r");
                if(file!=null){
                    file.seek(44);
                    while(true){
                        rsize = file.read(data, 0, data.length);
                        if (rsize < 0)break;
                        AIEngine.aiengine_feed(engine, data, rsize);
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        rv = AIEngine.aiengine_stop(engine);
    }

	
	
	//button events
	private void bindEvents() {
		initButton.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View v) {
            	if (initButton.getText().equals("Init")) {
            		
            		
                	setConfig();
            		//1) engine new
            		engine = AIEngine.aiengine_new(cfg.toString(), MainActivity.this);
                	runOnUiThread(new Runnable() {
                        public void run() {
                        	if(engine==0){
                            	mViewPager.setCurrentItem(1);
                            	detailText.setText("aiengine_new failed");
                            	recordmc.cancel();
                            	audioUrlText.setVisibility(View.GONE);
								urlText.setVisibility(View.GONE);
								toastShow("Engine initiate failed");
		                        return;
                            }else {
		                    	initButton.setEnabled(false);
		                    	recordButton.setEnabled(true);
		                    	replayButton.setEnabled(true);
		                    	deleteButton.setEnabled(true);
		                    	sourceSpinner.setEnabled(false);
		                    	cloudserverSpinner.setEnabled(false);
		                    	if (!source.equals("cloud")){
		                    		resSpinner.setEnabled(false);
		                    		coretypeSpinner.setEnabled(false);
		                    	}
		                    	toastShow("Engine initiate successfully");
		                    	rankText.setEnabled(true);
		                		useridText.setEnabled(true);
		                		precisionText.setEnabled(true);
		                		refText.setEnabled(true);
		                    	rankText.addTextChangedListener(textWatcher);
		                		useridText.addTextChangedListener(textWatcher);
		                		precisionText.addTextChangedListener(textWatcher);
		                		refText.addTextChangedListener(textWatcher);
                            }
	            		}
	                 });
            	}else {
            		return;
            	}
            }
        });
		
		
		recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub   
                if (recordButton.getText().equals("Record")) {
                	setParam();
                	
                	runOnUiThread(new Runnable() {
                        public void run() {
		                	replayButton.setEnabled(false);
		                	deleteButton.setEnabled(false);
		                	reRefText.setText("");
		                	audioUrlText.setText("");
		                	detailText.setText("");
		                	abstractText.setText("");
                        }
                	});
                	recordmc = new RecordMyCount(getRecordDuration(ref), 1000);
            		recordmc.start();
                	                	
                	
                    if (isAuto){
                    	toastShow("Automatically feed test audio");
                    	autoTest();
                    }else{                		          		
                		
                    	if (isRecording == false){
                            recorderStart();}
                    	byte[] id = new byte[64];
                        //2)engine start
                        rv = AIEngine.aiengine_start(engine, params.toString(), id, callback, MainActivity.this);
                        
                        if(rv!=0){
                        	runOnUiThread(new Runnable() {
                                public void run() {
                                	mViewPager.setCurrentItem(1);
                                	detailText.setText("aiengine_start failed");
                                	recordmc.cancel();
                                	replayButton.setEnabled(true);
                                	deleteButton.setEnabled(true);
                                	recordButton.setText("Record");
                                	recordButton.setBackgroundColor(android.graphics.Color.parseColor("#B4EEB4"));
                                	audioUrlText.setVisibility(View.GONE);
    								urlText.setVisibility(View.GONE);
                                }
                            });
                        	rv = AIEngine.aiengine_stop(engine);
                            return;
                        }
                    }
                    
                    
                }else {
                	recordmc.cancel();
                    if (!isAuto){
                    	
                    	recorderStop();
                    }
                     return;
            }
            }
        });
		
		replayButton.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View v) {
            	if (replayButton.getText().equals("Replay")) {
            		
            		replaymc = new ReplayMyCount(getRecordDuration(ref), 1000);
            		replaymc.start();
            		
            		playerStart();            		
            		runOnUiThread(new Runnable() {
                		public void run() {
                			replayButton.setText("Stop");
                			replayButton.setBackgroundColor(android.graphics.Color.parseColor("#F08080"));
                			recordButton.setEnabled(false);
                			deleteButton.setEnabled(false);
                		}
                	});
            	}else {
            		replaymc.cancel();
            		playerStop();
            		return;
            	}
            }
        });
		
		deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View v) {
            	if (deleteButton.getText().equals("Delete")) {
            		
            		//engine delete
            		rv = AIEngine.aiengine_delete(engine);
            		if (rv == 0){
            			engine =0;
            		runOnUiThread(new Runnable() {
                		public void run() {
                        	initButton.setEnabled(true);
                        	recordButton.setEnabled(false);
                        	replayButton.setEnabled(false);
                        	deleteButton.setEnabled(false);
	                    	sourceSpinner.setEnabled(true);
	                    	cloudserverSpinner.setEnabled(true);
	                    	if (!source.equals("cloud")){
		                    	resSpinner.setEnabled(true);
		                    	coretypeSpinner.setEnabled(true);
	                    	}
	                    	toastShow("Engine Delete Successfully");
	                    	rankText.setEnabled(false);
	                		useridText.setEnabled(false);
	                		precisionText.setEnabled(false);
	                		refText.setEnabled(false);
	                    	rankText.removeTextChangedListener(textWatcher);
	                		useridText.removeTextChangedListener(textWatcher);
	                		precisionText.removeTextChangedListener(textWatcher);
	                		refText.removeTextChangedListener(textWatcher);
                		}
                	});
            		}else {
            			runOnUiThread(new Runnable() {
                    		public void run() {
    	                    	toastShow("Engine Delete Failed");
    	                    	return;
                    		}
                    	});
            		}
            	}else {
            		return;
            	}
            }
        });
		
		
	}

}
