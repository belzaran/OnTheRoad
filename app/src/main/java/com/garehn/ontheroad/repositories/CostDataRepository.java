package com.garehn.ontheroad.repositories;

import androidx.lifecycle.LiveData;

import com.garehn.ontheroad.database.CostDao;
import com.garehn.ontheroad.trip.Cost;

import java.util.List;

public class CostDataRepository {

    private final CostDao costDao;

    public CostDataRepository(CostDao itemDao) { this.costDao = itemDao; }

    // --- GET ---

    public List<Cost> getCosts(long userId){ return this.costDao.getCosts(userId); }

    // --- CREATE ---

    public void createCost(Cost cost){ costDao.insertCost(cost); }

    // --- DELETE ---
    public void deleteCost(long costId){ costDao.deleteCost(costId); }

    // --- UPDATE ---
    public void updateCost(Cost cost){ costDao.updateCost(cost); }

}