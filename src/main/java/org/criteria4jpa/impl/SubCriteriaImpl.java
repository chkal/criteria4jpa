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
  private final JoinType joinType;

  /**
   * Creates a new {@link SubCriteriaImpl}
   * @param root Reference to the root criteria
   * @param parent Reference to parent criteria
   * @param path relative path to the references entity or collection
   * @param alias Custom alias for this subcriteria (may be <code>null</code>)
   * @param joinType The type of join to perform
   */
  public SubCriteriaImpl(CriteriaImpl root, Criteria parent, String path, 
      String alias, JoinType joinType) {
    this.root = root;
    this.parent = parent;
    this.path = path;
    this.alias = alias;
    this.joinType = joinType;
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

  public <E> List<E> getResultList() {
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

  public <E> E getSingleResult() {
    return root.getSingleResult();
  }

  public Object getSingleResultOrNull() {
    return root.getSingleResultOrNull();
  }
  
  public String getAlias() {
    return alias;
  }

  public Criteria createCriteria(String associationPath) {
    return root.createCriteria(this, associationPath, null, JoinType.INNER_JOIN);
  }
  
  public Criteria createCriteria(String associationPath, JoinType joinType) {
    return root.createCriteria(this, associationPath, null, joinType);
  }

  public Criteria createCriteria(String associationPath, String alias) {
    return root.createCriteria(this, associationPath, alias, JoinType.INNER_JOIN);
  }
  
  public Criteria createCriteria(String associationPath, String alias, JoinType joinType) {
    return root.createCriteria(this, associationPath, alias, joinType);
  }
  
  public Criteria setProjection(Projection projection) {
    root.setProjection(this, projection);
    return this;
  }
  
  public Criteria setFetchMode(String relativePath, FetchMode fetchMode) {
    root.setFetchMode(this, relativePath, fetchMode);
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

  public JoinType getJoinType() {
    return joinType;
  }
}
