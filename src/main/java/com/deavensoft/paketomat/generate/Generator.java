package com.deavensoft.paketomat.generate;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;

@Slf4j

public class Generator {

    private static final Integer FOUR_DIGIT_BOUND_FOR_PIN_CODE = 10000;


    public static String generateCode() {
        SecureRandom pinCodeForPaketomat = new SecureRandom();
        int generateNumberForPaketomat = pinCodeForPaketomat.nextInt(FOUR_DIGIT_BOUND_FOR_PIN_CODE);
        String formatted = String.format("%04d", generateNumberForPaketomat);
        String fourDigitFormatted = StringUtils.leftPad(formatted, 4, "0");
        log.info("Code is generated for picking up the package");
        return fourDigitFormatted;

    }

}
