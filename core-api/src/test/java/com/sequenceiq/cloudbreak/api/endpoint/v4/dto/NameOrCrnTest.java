package com.sequenceiq.cloudbreak.api.endpoint.v4.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.rules.ExpectedException.none;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class NameOrCrnTest {

    private static final String CRN = "my-crn";

    private static final String NAME = "my-name";

    private static final String EXCEPTION_MSG_BOTH = "Only either the name or the CRN has to be given, neither both nor none of them.";

    @Rule
    public final ExpectedException thrown = none();

    @Test
    public void testOfName() {
        NameOrCrn nameOrCrn = NameOrCrn.ofName(NAME);

        assertEquals(NAME, nameOrCrn.name);
    }

    @Test
    public void testOfNameWhenNull() {
        thrown.expectMessage("Name must be provided.");
        thrown.expect(IllegalArgumentException.class);

        NameOrCrn.ofName(null);
    }

    @Test
    public void testOfNameWhenZeroLength() {
        thrown.expectMessage("Name must be provided.");
        thrown.expect(IllegalArgumentException.class);

        NameOrCrn.ofName("");
    }

    @Test
    public void testOfCrn() {
        NameOrCrn nameOrCrn = NameOrCrn.ofCrn(CRN);

        assertEquals(CRN, nameOrCrn.crn);
    }

    @Test
    public void testOfCrnWhenNull() {
        thrown.expectMessage("Crn must be provided.");
        thrown.expect(IllegalArgumentException.class);

        NameOrCrn.ofCrn(null);
    }

    @Test
    public void testOfCrnWhenZeroLength() {
        thrown.expectMessage("Crn must be provided.");
        thrown.expect(IllegalArgumentException.class);

        NameOrCrn.ofCrn("");
    }

    @Test
    public void testToStringWhenName() {
        NameOrCrn nameOrCrn = NameOrCrn.ofName(NAME);

        assertEquals("[NameOrCrn of name: 'my-name']", nameOrCrn.toString());
    }

    @Test
    public void testToStringWhenCrn() {
        NameOrCrn nameOrCrn = NameOrCrn.ofCrn(CRN);

        assertEquals("[NameOrCrn of crn: 'my-crn']", nameOrCrn.toString());
    }

    @Test
    public void testHasName() {
        NameOrCrn nameOrCrn = NameOrCrn.ofName(NAME);

        assertTrue(nameOrCrn.hasName());
    }

    @Test
    public void testHasNameWhenEmpty() {
        NameOrCrn nameOrCrn = NameOrCrn.ofCrn(CRN);

        assertFalse(nameOrCrn.hasName());
    }

    @Test
    public void testHasCrn() {
        NameOrCrn nameOrCrn = NameOrCrn.ofCrn(CRN);

        assertTrue(nameOrCrn.hasCrn());
    }

    @Test
    public void testHasCrnWhenEmpty() {
        NameOrCrn nameOrCrn = NameOrCrn.ofName(NAME);

        assertFalse(nameOrCrn.hasCrn());
    }

    @Test
    public void testReaderGetName() {
        NameOrCrn nameOrCrn = NameOrCrn.ofName(NAME);

        assertEquals(NAME, nameOrCrn.getName());
    }

    @Test
    public void testReaderGetCrn() {
        NameOrCrn nameOrCrn = NameOrCrn.ofCrn(CRN);

        assertEquals(CRN, nameOrCrn.getCrn());
    }

    @Test
    public void testGetNameWhenCrnProvided() {
        thrown.expectMessage("Request to get name when crn was provided on [NameOrCrn of crn: 'my-crn']");
        thrown.expect(IllegalArgumentException.class);
        NameOrCrn nameOrCrn = NameOrCrn.ofCrn(CRN);

        nameOrCrn.getName();
    }

    @Test
    public void testGetCrnWhenCrnProvided() {
        thrown.expectMessage("Request to get crn when name was provided on [NameOrCrn of name: 'my-name']");
        thrown.expect(IllegalArgumentException.class);
        NameOrCrn nameOrCrn = NameOrCrn.ofName(NAME);

        nameOrCrn.getCrn();
    }

    @Test
    public void testOfBothWhenNeitherOfTheArgumentsAreProvided() {
        thrown.expectMessage(EXCEPTION_MSG_BOTH);
        thrown.expect(IllegalArgumentException.class);
        NameOrCrn.ofBoth(null, null);
    }

    @Test
    public void testOfBothWhenOnlyNameProvided() {
        thrown.expectMessage(EXCEPTION_MSG_BOTH);
        thrown.expect(IllegalArgumentException.class);
        NameOrCrn.ofBoth(NAME, null);
    }

    @Test
    public void testOfBothWhenOnlyCRNProvided() {
        thrown.expectMessage(EXCEPTION_MSG_BOTH);
        thrown.expect(IllegalArgumentException.class);
        NameOrCrn.ofBoth(null, CRN);
    }

    @Test
    public void testOfBothWhenArgumentsAreProvidedButAsEmptyString() {
        thrown.expectMessage(EXCEPTION_MSG_BOTH);
        thrown.expect(IllegalArgumentException.class);
        NameOrCrn.ofBoth("", "");
    }

    @Test
    public void testOfBothWhenCRNIsEmptyStringAndNameProvided() {
        thrown.expectMessage(EXCEPTION_MSG_BOTH);
        thrown.expect(IllegalArgumentException.class);
        NameOrCrn.ofBoth(NAME, "");
    }

    @Test
    public void testOfBothWhenNameIsEmptyStringAndCRNProvided() {
        thrown.expectMessage(EXCEPTION_MSG_BOTH);
        thrown.expect(IllegalArgumentException.class);
        NameOrCrn.ofBoth("", CRN);
    }

    @Test
    public void testOfBothWhenBothOfTheArgumentsAreProvided() {
        NameOrCrn result = NameOrCrn.ofBoth(NAME, CRN);

        assertEquals(NAME, result.getName());
        assertEquals(CRN, result.getCrn());
    }

}
