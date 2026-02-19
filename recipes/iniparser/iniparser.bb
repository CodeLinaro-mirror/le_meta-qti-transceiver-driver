SUMMARY = "Stand-alone ini file parsing library"
DESCRIPTION = "Stand-alone iniparser library."
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://LICENSE;md5=8474d3b745f77e203f1fc82fb0bb7678"
PV = "4.2"

SRC_URI = "https://github.com/ndevilla/iniparser/archive/v${PV}.tar.gz"

SRC_URI[sha256sum] = "dbcbaf3aedb4f88a9fc0df4b315737ddd10e6c37918e3d89f0ecc475333bde4d"
SRC_URI[md5sum] = "f52458ace1466c5798c47aa3e7a9b949"

S = "${WORKDIR}/iniparser-${PV}"

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${libdir}
    install -d ${D}${includedir}

    # Install the shared library
    install -m 0755 libiniparser.so.1 ${D}${libdir}/libiniparser.so.1
    ln -sf libiniparser.so.1 ${D}${libdir}/libiniparser.so

    # Install headers
    install -m 0644 src/iniparser.h ${D}${includedir}/
    install -m 0644 src/dictionary.h ${D}${includedir}/
}

# Define the package contents
FILES_${PN} = "${libdir}/*.so.*"
FILES_${PN}-dev = "${includedir}/*.h ${libdir}/*.so"

CFLAGS += "-fPIC"