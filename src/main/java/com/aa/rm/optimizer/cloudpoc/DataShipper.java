package com.aa.rm.optimizer.cloudpoc;

import com.aa.rm.optimizer.common.model.BPCCabinData;
import com.aa.rm.optimizer.common.model.BPCData;
import com.aa.rm.optimizer.common.model.BidPriceData;
import com.aa.rm.optimizer.common.model.ProcessStatus;
import org.openspaces.core.GigaSpace;
import org.openspaces.events.adapter.SpaceDataEvent;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class DataShipper
{
	private GigaSpace gigaSpace;
	private Comparator<Integer> byBidPrice;
	private final static Logger log = Logger.getLogger(DataShipper.class.getName());

	public DataShipper()
	{
		// To-Do: Sort by float
		// Sort bid price from high to low
		//
		byBidPrice = (Integer bp1, Integer bp2) -> Integer.compare(bp2, bp1);
	}

	@SpaceDataEvent
	public BPCData processBidPriceCurve(BPCData bpc) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			log.info("Receiving message (BPCData) in processBidPriceCurve().. " + bpc.getFlightId() + "," + df.format(bpc.getDepartureDate()) + "," + bpc.getOrigin() + "," + bpc.getDest());
			bpc.setStatus(ProcessStatus.PROCESSED);
		} catch (Exception e) {
			log.warning("Failed to process message (BPCData) in processBidPriceCurve().. " + bpc.getFlightId() + "," + df.format(bpc.getDepartureDate()) + "," + bpc.getOrigin() + "," + bpc.getDest());
			e.printStackTrace();
			bpc.setStatus(ProcessStatus.FAILED);
		}
		return bpc;
	}

	public GigaSpace getGigaSpace() {
		return gigaSpace;
	}

	public void setGigaSpace(GigaSpace gigaSpace) {
		this.gigaSpace = gigaSpace;
	}
}
