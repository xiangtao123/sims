package com.jsrush.security.rbac.repository;

import com.jsrush.security.rbac.entity.Ec;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EcDao extends PagingAndSortingRepository<Ec, Long> ,EcDaoCustom{

	List<Ec> findAll();

	List<Ec> findByCorpName(String name);

	List<Ec> findByCardid(String cardid);

	List<Ec> findByCorpAccount(String corpAccount);
}
