package org.opentripplanner.netex.loader.parser;

import org.opentripplanner.netex.loader.NetexImportDataIndex;
import org.rutebanken.netex.model.Authority;
import org.rutebanken.netex.model.Organisation_VersionStructure;
import org.rutebanken.netex.model.OrganisationsInFrame_RelStructure;
import org.rutebanken.netex.model.ResourceFrame_VersionFrameStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.Collection;

class ResourceFrameParser extends NetexParser<ResourceFrame_VersionFrameStructure> {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceFrameParser.class);

    private final Collection<Authority> authorityById = new ArrayList<>();

    @Override
    void parse(ResourceFrame_VersionFrameStructure frame) {
        parseOrganization(frame.getOrganisations());

        // Keep list sorted alphabetically
        warnOnMissingMapping(LOG, frame.getBlacklists());
        warnOnMissingMapping(LOG, frame.getControlCentres());
        warnOnMissingMapping(LOG, frame.getDataSources());
        warnOnMissingMapping(LOG, frame.getEquipments());
        warnOnMissingMapping(LOG, frame.getGroupsOfEntities());
        warnOnMissingMapping(LOG, frame.getGroupsOfOperators());
        warnOnMissingMapping(LOG, frame.getOperationalContexts());
        warnOnMissingMapping(LOG, frame.getResponsibilitySets());
        warnOnMissingMapping(LOG, frame.getSchematicMaps());
        warnOnMissingMapping(LOG, frame.getTypesOfValue());
        warnOnMissingMapping(LOG, frame.getVehicles());
        warnOnMissingMapping(LOG, frame.getVehicleEquipmentProfiles());
        warnOnMissingMapping(LOG, frame.getVehicleModels());
        warnOnMissingMapping(LOG, frame.getVehicleTypes());
        warnOnMissingMapping(LOG, frame.getWhitelists());
        warnOnMissingMapping(LOG, frame.getZones());

        verifyCommonUnusedPropertiesIsNotSet(LOG, frame);
    }

    @Override void setResultOnIndex(NetexImportDataIndex netexIndex) {
        netexIndex.authoritiesById.addAll(authorityById);
    }


    /* private methods */

    private void parseOrganization(OrganisationsInFrame_RelStructure elements) {
        for (JAXBElement e : elements.getOrganisation_()) {
            parseOrganization((Organisation_VersionStructure) e.getValue());
        }
    }

    private void parseOrganization(Organisation_VersionStructure element) {
        if (element instanceof Authority) {
            authorityById.add((Authority) element);
        } else {
            warnOnMissingMapping(LOG, element);
        }
    }
}