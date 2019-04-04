package com.tim10.domain;

import java.util.Set;

public class RentACar extends Company {

	private Set<BranchOffice> branchOffices;
	private Set<RentACarAdmin> administrators;
	private Set<QuickVehicleReservation> quickVehicleReservations;
	public PriceList additionalServicesPriceList;

	public RentACar() {
		super();
	}

	public Set<BranchOffice> getBranchOffices() {
		return branchOffices;
	}

	public Set<RentACarAdmin> getAdministrators() {
		return administrators;
	}

	public Set<QuickVehicleReservation> getQuickVehicleReservations() {
		return quickVehicleReservations;
	}

	public PriceList getAdditionalServicesPriceList() {
		return additionalServicesPriceList;
	}

	public void setBranchOffices(Set<BranchOffice> branchOffices) {
		this.branchOffices = branchOffices;
	}

	public void setAdministrators(Set<RentACarAdmin> administrators) {
		this.administrators = administrators;
	}

	public void setQuickVehicleReservations(Set<QuickVehicleReservation> quickVehicleReservations) {
		this.quickVehicleReservations = quickVehicleReservations;
	}

	public void setAdditionalServicesPriceList(PriceList additionalServicesPriceList) {
		this.additionalServicesPriceList = additionalServicesPriceList;
	}
}