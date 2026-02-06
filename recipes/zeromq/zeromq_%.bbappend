SUMMARY = "ZeroMQ messaging library"
DESCRIPTION = "ZeroMQ is a high-performance asynchronous messaging library"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"

SRC_URI[sha256sum] = "6653ef5910f17954861fe72332e68b03ca6e4d9c7160eb3a8de5a5a913bfab43"
SRC_URI[md5sum] = "ae933b1e98411fd7cb8309f9502d2737"


SRC_URI = "https://github.com/zeromq/libzmq/releases/download/v4.3.5/zeromq-${PV}.tar.gz \
        file://0001-CMakeLists-txt-Avoid-host-specific-path-to-libsodium.patch \
        file://run-ptest \
"

FILES_${PN}-doc += "${datadir}/zmq/*.txt"

EXTRA_OECMAKE = "-DZMQ_BUILD_DRAFT_API=1 -DENABLE_DRAFTS=ON -DBUILD_TESTS=OFF"

PV = "4.3.5"
