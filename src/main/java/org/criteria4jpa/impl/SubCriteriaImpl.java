package org.criteria4jpa.impl;

import java.util.List;


import org.criteria4jpa.Criteria;
import org.criteria4jpa.criterion.Criterion;
import org.criteria4jpa.order.Order;
import org.criteria4jpa.projection.Projection;

public class SubCriteriaImpl implements Criteria {
  
  private final CriteriaImpl root;
  private final Criteria parent;
  private final String path;
  private final String alias;
  
  /**
   * constructor
   * @param parent parent criteria
   * @param alias alias for this subcriteria
   */
  public SubCriteriaImpl(CriteriaImpl root, Criteria parent, String path, String alias) {
    this.root = root;
    this.parent = parent;
    this.path = path;
    this.alias = alias;
  }

  /*
   * implementation methods delegate to parent
   */
  
  public Criteria add(Criterion criterion) {
    root.add(this, criterion);
    return this;
  }

  public Criteria addOrder(Order order) {
    root.addOrder(this, order);
    return this;
  }

  @SuppressWarnings("unchecked")
  public List getResultList() {
    return root.getResultList();
  }

  public Criteria setFirstResult(int firstResult) {
    root.setFirstResult(firstResult);
    return this;
  }

  public Criteria setMaxResults(int maxResults) {
    root.setMaxResults(maxResults);
    return this;
  }

  public Object getSingleResult() {
    return root.getSingleResult();
  }

  public String getAlias() {
    return alias;
  }

  public Criteria createCriteria(String associationPath) {
    return createCriteria(associationPath, null);
  }

  public Criteria createCriteria(String associationPath, String alias) {
    return root.createCriteria(this, associationPath, alias);
  }
  
  public Criteria setProjection(Projection projection) {
    root.setProjection(this, projection);
    return this;
  }

  public String getPath() {
    return path;
  }

  public Criteria getParent() {
    return parent;
  }

  public CriteriaImpl getRoot() {
    return root;
  }

}
