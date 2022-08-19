DESCRIPTION = "Lassen QSFP transceiver drivers"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

inherit linux-kernel-base deploy

PR = "r0"

DEPENDS = "rsync-native"
DEPENDS += "bc-native bison-native"

do_configure[depends] += "${@oe.utils.conditional('KERNEL_USE_PREBUILTS', 'True', 'virtual/kernel:do_prebuilt_shared_workdir', 'virtual/kernel:do_shared_workdir',d)}"

FILESPATH   =+ "${WORKSPACE}:"
SRC_URI     =  "file://transceiver-driver/drivers/"

S = "${WORKDIR}/transceiver-driver/drivers/"

EXTRA_OEMAKE += "TARGET_SUPPORT=${BASEMACHINE}"

# Disable parallel make
PARALLEL_MAKE = ""

do_compile() {

    cd ${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform  && \

    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    EXT_MODULES=../../transceiver-driver/drivers \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${WORKDIR}/transceiver-driver/drivers \
    OUT_DIR=${KERNEL_OUT_PATH}/ \
    KERNEL_UAPI_HEADERS_DIR=${STAGING_KERNEL_BUILDDIR} \
    ./build/build_module.sh
}

do_install() {
    install -d ${D}${sysconfdir}/initscripts
    install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/
    install -d ${D}/usr/include/
    install -d ${D}/usr/lib/modules/

    ${STAGING_DIR_NATIVE}/usr/libexec/aarch64-oe-linux/gcc/aarch64-oe-linux/9.3.0/strip \
    --strip-debug ${WORKDIR}/transceiver-driver/drivers/fpc_qsfp.ko

    LD_LIBRARY_PATH=${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform/prebuilts/kernel-build-tools/linux-x86/lib64/ \
    ${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
    ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509 ${WORKDIR}/transceiver-driver/drivers/fpc_qsfp.ko

    install -m 0755 ${WORKDIR}/transceiver-driver/drivers/fpc_qsfp.ko -D ${WORKDIR}/fpc_qsfp.ko
    install -m 0755 ${WORKDIR}/transceiver-driver/drivers/fpc_qsfp.ko -D ${D}${libdir}/modules/fpc_qsfp.ko
}

do_deploy() {
    cp -rp ${WORKDIR}/fpc_qsfp.ko ${DEPLOYDIR}/
}

addtask do_deploy after do_install

FILES_${PN} += "${sysconfdir}/*"
FILES_${PN} += "${libdir}/modules/*"

