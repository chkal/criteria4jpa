package org.criteria4jpa.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.criteria4jpa.Criteria;
import org.criteria4jpa.criterion.Criterion;
import org.criteria4jpa.order.Order;
import org.criteria4jpa.projection.Projection;

public class CriteriaImpl implements Criteria {

  // basic stuff
  private final EntityManager entityManager;
  private final String entityName;
  private final String alias;
  
  // properties
  private final List<MetaEntry<Criterion>> criterionList = new ArrayList<MetaEntry<Criterion>>();
  private final List<MetaEntry<Order>> orderList = new ArrayList<MetaEntry<Order>>();
  private final List<MetaEntry<PathFetchMode>> fetchTypeList = new ArrayList<MetaEntry<PathFetchMode>>();
  private final List<SubCriteriaImpl> subcriteriaList = new ArrayList<SubCriteriaImpl>();
  private MetaEntry<Projection> projectionEntry;
  private Integer maxResults;
  private Integer firstResult;
  

  public CriteriaImpl(EntityManager entityManager, String name) {
    this(entityManager, name, null);
  }

  public CriteriaImpl(EntityManager entityManager, String name, String alias) {
    this.entityManager = entityManager;
    this.entityName = name;
    this.alias = alias;
  }

  /*
   * internal
   */

  public void addOrder(Criteria criteria, Order order) {
    this.orderList.add(new MetaEntry<Order>(criteria, order));
  }
  
  public Criteria add(Criteria criteria, Criterion criterion) {
    this.criterionList.add(new MetaEntry<Criterion>(criteria, criterion));
    return this;
  }
  
  protected Criteria createCriteria(Criteria parent, String associationPath, 
      String alias, JoinType joinType) {
    SubCriteriaImpl subcriteria = 
      new SubCriteriaImpl(this, parent, associationPath, alias, joinType);
    subcriteriaList.add(subcriteria);
    return subcriteria;
  }
  
  protected void setProjection(Criteria criteria, Projection projection) {
    projectionEntry = new MetaEntry<Projection>(criteria, projection);
  }
  
  public Criteria setFetchMode(Criteria criteria, String relativePath, FetchMode fetchMode) {
    this.fetchTypeList.add( new MetaEntry<PathFetchMode>(criteria, 
        new PathFetchMode(relativePath, fetchMode)) );
    return this;
  }

  /*
   * interface impl
   */
  
  public Criteria add(Criterion criterion) {
    add(this, criterion);
    return this;
  }

  public Criteria addOrder(Order order) {
    addOrder(this, order);
    return this;
  }

  public Criteria createCriteria(String associationPath) {
    return createCriteria(this, associationPath, null, JoinType.INNER_JOIN);
  }
  
  public Criteria createCriteria(String associationPath, JoinType joinType) {
    return createCriteria(this, associationPath, null, joinType);
  }
  
  public Criteria createCriteria(String associationPath, String alias) {
    return createCriteria(this, associationPath, alias, JoinType.INNER_JOIN);
  }

  public Criteria createCriteria(String associationPath, String alias, JoinType joinType) {
    return createCriteria(this, associationPath, alias, joinType);
  }
  
  public Criteria setProjection(Projection projection) {
    setProjection(this, projection);
    return this;
  }
  
  public Criteria setFirstResult(int firstResult) {
    this.firstResult = Integer.valueOf(firstResult);
    return this;
  }

  public Criteria setMaxResults(int maxResults) {
    this.maxResults = Integer.valueOf(maxResults);
    return this;
  }
  
  public Criteria setFetchMode(String relativePath, FetchMode fetchMode) {
    this.setFetchMode(this, relativePath, fetchMode);
    return this;
  }
  
  /*
   * execute query
   */
  
  @SuppressWarnings("unchecked")
  public <E> List<E> getResultList() {
    return buildQuery().getResultList();
  }
  
  @SuppressWarnings("unchecked")
  public <E> E getSingleResult() {
    return (E) buildQuery().getSingleResult();
  }

  public Object getSingleResultOrNull() {
    try {
      return getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }
  
  /*
   * debugging
   */
  
  @Override
  public String toString() {
    return new CriteriaQueryBuilder(this).createQueryDebugString();
  }
  
  /*
   * internal stuff
   */

  private Query buildQuery() {
    CriteriaQueryBuilder queryBuilder = new CriteriaQueryBuilder(this);
    return queryBuilder.createQuery(entityManager);
  }

  /*
   * getter
   */
  
  public Integer getMaxResults() {
    return maxResults;
  }
  
  public Integer getFirstResult() {
    return firstResult;
  }

  public String getEntityName() {
    return entityName;
  }

  public String getAlias() {
    return alias;
  }

  public List<MetaEntry<Criterion>> getCriterionList() {
    return criterionList;
  }

  public List<MetaEntry<Order>> getOrderList() {
    return orderList;
  }

  public List<SubCriteriaImpl> getSubcriteriaList() {
    return subcriteriaList;
  }

  public MetaEntry<Projection> getProjectionEntry() {
    return projectionEntry;
  }

  public List<MetaEntry<PathFetchMode>> getFetchTypeList() {
    return fetchTypeList;
  }

}

