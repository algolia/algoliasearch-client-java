package com.algolia.search.responses;

import com.algolia.search.objects.Log;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Logs implements Serializable {

  private List<Log> logs;

  public List<Log> getLogs() {
    return logs == null ? new ArrayList<>() : logs;
  }

  @SuppressWarnings("unused")
  public Logs setLogs(List<Log> logs) {
    this.logs = logs;
    return this;
  }

  @Override
  public String toString() {
    return "Logs{" + "logs=" + logs + '}';
  }
}
