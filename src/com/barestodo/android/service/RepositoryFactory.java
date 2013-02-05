package com.barestodo.android.service;

import com.barestodo.android.service.impl.RestServerRepository;
import com.barestodo.android.service.impl.StubPlaceRepository;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 26/01/13
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */
public class RepositoryFactory {

    public static IPlaceRepository getPlaceRepository(){
        //return RestServerRepository.INSTANCE;
        return StubPlaceRepository.INSTANCE;
    }
}
