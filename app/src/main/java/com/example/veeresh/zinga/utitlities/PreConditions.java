package com.example.veeresh.zinga.utitlities;

import android.support.annotation.Nullable;

public class PreConditions {

  public static <T> T checkNotNull(T reference) {
    if (reference == null) {
      throw new NullPointerException();
    } else {
      return reference;
    }
  }

  public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
    if (reference == null) {
      throw new NullPointerException(String.valueOf(errorMessage));
    } else {
      return reference;
    }
  }
}
