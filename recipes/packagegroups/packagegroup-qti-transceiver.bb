SUMMARY = "Transceiver open source package groups"
LICENSE = "GPL-2.0"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-transceiver \
    '

RDEPENDS_packagegroup-qti-transceiver = ' \
    transceiver-dlkm \
    '
