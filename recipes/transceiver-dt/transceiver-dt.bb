DESCRIPTION = "Lassen QSFP transceiver devicetree"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

inherit linux-kernel-base deploy

PR = "r0"

FILESPATH   =+ "${WORKSPACE}:"
SRC_URI     =  "file://src/transceiver-driver/devicetree/"

S = "${WORKDIR}/src/transceiver-driver/devicetree/"

do_configure[depends] = "virtual/kernel:do_shared_workdir"

EXTRA_OEMAKE += "TARGET_SUPPORT=${BASEMACHINE}"

# Disable parallel make
PARALLEL_MAKE = ""

RM_WORK_EXCLUDE += "${PN}"

do_compile() {
    cd ${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform  && \
    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    EXT_MODULES=../../../src/transceiver-driver/devicetree \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${WORKDIR}/src/transceiver-driver/devicetree \
    OUT_DIR=${KERNEL_OUT_PATH}/ \
    ./build/build_module.sh
}

do_deploy() {
    install -d ${DEPLOYDIR}/build-artifacts/techpack-dtbos
    cp -a \
    ${WORKDIR}/src/transceiver-driver/devicetree/*.dtbo \
    ${DEPLOYDIR}/build-artifacts/techpack-dtbos/
}

addtask do_deploy after do_install
