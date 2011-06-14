package org.criteria4jpa.impl;

import org.criteria4jpa.Criteria.FetchMode;

class PathFetchMode {

  private final String relativePath;
  private final FetchMode fetchType;

  public PathFetchMode(String relativePath, FetchMode fetchMode) {
    this.relativePath = relativePath;
    this.fetchType = fetchMode;
  }

  public String getRelativePath() {
    return relativePath;
  }

  public FetchMode getFetchMode() {
    return fetchType;
  }

}
