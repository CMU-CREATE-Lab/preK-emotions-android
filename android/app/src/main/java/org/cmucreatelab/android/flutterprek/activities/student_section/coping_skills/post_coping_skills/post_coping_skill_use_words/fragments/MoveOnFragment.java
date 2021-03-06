package org.cmucreatelab.android.flutterprek.activities.student_section.coping_skills.post_coping_skills.post_coping_skill_use_words.fragments;

import android.util.Log;
import android.view.View;

import org.cmucreatelab.android.flutterprek.Constants;
import org.cmucreatelab.android.flutterprek.GlobalHandler;
import org.cmucreatelab.android.flutterprek.R;
import org.cmucreatelab.android.flutterprek.audio.AudioPlayer;

import java.io.File;

public class MoveOnFragment extends UseWordsFragment {


    @Override
    public int getInflatedLayoutResource() {
        return R.layout._coping_skill__fragment_move_on;
    }


    @Override
    public void initializeFragment() {
        final ActivityCallback activityCallback = getActivityCallback();
        getFragmentView().findViewById(R.id.imageViewNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCallback.setFragment(FragmentState.RECORD);
            }
        });
        getFragmentView().findViewById(R.id.imageViewYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCallback.goToNextActivity();
            }
        });
        getFragmentView().findViewById(R.id.layoutPlayButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecordedAudio(true);
            }
        });
    }


    public void addRecordedAudio(boolean playback) {
        ActivityCallback activityCallback = getActivityCallback();

        // release timers if we are playing back audio
        activityCallback.releaseOverlayTimers();

        File recordedAudioFile = GlobalHandler.getInstance(getActivity().getApplicationContext()).studentSectionNavigationHandler.recordedAudioFile;
        if (recordedAudioFile != null) {
            Log.d(Constants.LOG_TAG, "Added audio to play: " + recordedAudioFile.getAbsolutePath());
            AudioPlayer audioPlayer = AudioPlayer.getInstance(getActivity().getApplicationContext());
            if (playback) {
                audioPlayer.stop();
                audioPlayer.addAudioFromInternalStorage(recordedAudioFile.getAbsolutePath(), activityCallback);
                audioPlayer.playAudio();
            } else {
                audioPlayer.addAudioFromInternalStorage(recordedAudioFile.getAbsolutePath());
            }
        }
    }

}
