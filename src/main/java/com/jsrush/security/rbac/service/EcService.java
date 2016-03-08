package com.jsrush.security.rbac.service;

import com.jsrush.security.rbac.entity.Ec;
import com.jsrush.security.rbac.entity.Role;
import com.jsrush.security.rbac.repository.EcDao;
import com.jsrush.security.rbac.repository.RoleDao;
import com.jsrush.security.rbac.vo.EcDTO;
import com.jsrush.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class EcService {

    // private Logger log = LoggerFactory.getLogger(EcService.class);

    @Autowired
    private EcDao ecDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleService roleService;

    @Transactional(readOnly = false)
    public void saveEc(Ec entity) {
        ecDao.save(entity);
    }

    public Ec getEc(Long id) {
        return ecDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public Ec saveByEcId(Long ecId, Long[] permissions) {
        Ec ec = new Ec();
        for (Long rId : permissions) {
            Role rl = roleDao.findOne(rId);
            if (rl == null)
                continue;
            ec.getRoles().add(rl);
        }
        ecDao.save(ec);
        return ec;
    }

    @Transactional(readOnly = false)
    public void saveOrUpdateData(EcDTO dto, List<Long> roleIds) {
        Ec entity = null;
        if (dto.getId() != null && dto.getId() > 0) {
            entity = ecDao.findOne(dto.getId());
        }
        if (entity == null) {
            entity = new Ec();
            updateEc(entity, dto);
            entity.setCreateTime(DateUtil.getNowTimestamp());
        } else {
            updateEc(entity, dto);
            entity.setUpdateTime(DateUtil.getNowTimestamp());
        }
        entity.getRoles().clear();

        Set<Role> roles = roleService.getRolesToOther(roleIds);
        entity.getRoles().addAll(roles);
        ecDao.save(entity);
    }

    private void updateEc(Ec entity, EcDTO dto) {
        entity.setActionState(dto.getActionState());
        entity.setBizcode(dto.getBizcode());
        entity.setCardid(dto.getCardid());
        entity.setCorpAccount(dto.getCorpAccount());
        entity.setCorpName(dto.getCorpName());
        entity.setCustomerMng(dto.getCustomerMng());
        entity.setCustomerMngNo(dto.getCustomerMngNo());
        entity.setEmail(dto.getEmail());
        entity.setEnglishName(dto.getEnglishName());
        entity.setEshortName(dto.getShortName());
        entity.setFax(dto.getFax());
        entity.setGroupLogoUrl(dto.getGroupLogoURL());
        entity.setLicense(dto.getLicense());
        entity.setLinkMan(dto.getLinkMan());
        entity.setMgrLinkNo(dto.getMgrLinkNo());
        entity.setPhoneNum(dto.getPhoneNum());
        entity.setPostCode(dto.getPostCode());
        entity.setShortName(dto.getShortName());
        entity.setWapUrl(dto.getWapURL());
        entity.setWwwUrl(dto.getWwwURL());
        entity.setCorpCode(dto.getCorpCode());
        entity.setRegistrationNo(dto.getRegistrationNo());
    }

    public List<Ec> getAll() {
        return ecDao.findAll();
    }

    @SuppressWarnings("unchecked")
    public List<Ec> findByCorpNameLike(String name, int index, int pageSize) {
        return ecDao.findByCorpNameLike(name, index, pageSize);
    }

    public int findCountByCorpNameLike(String name) {
        return ecDao.findCountByCorpNameLike(name);
    }

    public boolean valadateCorpName(String name) {
        return ecDao.findByCorpName(name).size() == 0;
    }

    public boolean validateCardid(String cardId) {
        return ecDao.findByCardid(cardId).size() == 0;
    }

    public boolean validateCorpAccount(String corpAccount) {
        return ecDao.findByCorpAccount(corpAccount).size() == 0;
    }

    @Transactional(readOnly = false)
    public boolean delByIds(List<Long> ids) {
        Iterable<Ec> list = ecDao.findAll(ids);
        if (!list.iterator().hasNext())
            return false;
        ecDao.delete(list);
        return true;
    }
}
