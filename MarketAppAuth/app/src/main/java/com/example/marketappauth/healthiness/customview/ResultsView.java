/*
 * Author :  Thamal Wijetunge
 */

package com.example.marketappauth.healthiness.customview;

import com.example.marketappauth.healthiness.tflite.Classifier.Recognition;

import java.util.List;

public interface ResultsView {
  public void setResults(final List<Recognition> results);
}
