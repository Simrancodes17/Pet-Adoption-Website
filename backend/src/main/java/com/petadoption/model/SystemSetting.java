package com.petadoption.model;

/**
 * System-wide configurations managed by Admin.
 */
public record SystemSetting(
    String platformName,
    String supportEmail,
    int adoptionFee,
    int maxListings,
    int autoArchiveDays,
    boolean requireApproval,
    boolean emailAlerts
) {}
