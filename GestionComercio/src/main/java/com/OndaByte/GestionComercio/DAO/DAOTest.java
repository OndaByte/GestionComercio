package com.OndaByte.GestionComercio.DAO;

import com.OndaByte.GestionComercio.modelo.Test;

/**
 * DAOTest
 */
public class DAOTest extends ABMDAO<Test> {

	@Override
	public Class<Test> getClase() {
        return Test.class;
	}

	@Override
	public String getClave() {
        return "id";
	}
}
