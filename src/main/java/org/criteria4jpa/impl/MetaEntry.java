package org.criteria4jpa.impl;

import org.criteria4jpa.Criteria;

public class MetaEntry<E> {

  private final Criteria criteria;
  private final E entry;

  public MetaEntry(Criteria criteria, E entry) {
    this.criteria = criteria;
    this.entry = entry;
  }

  public Criteria getCriteria() {
    return criteria;
  }

  public E getEntry() {
    return entry;
  }
  
}
