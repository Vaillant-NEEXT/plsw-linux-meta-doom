SUMMARY = "Open Source Doom Engine"
HOMEPAGE = "http://zdoom.org"
SECTION = "games"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d32239bcb673463ab874e80d47fae504"

DEPENDS = "\
    bzip2-replacement-native \
    libsdl2 \
    libvpx \
    libwebp \
    zmusic \
    "

SRC_URI = "git://github.com/ZDoom/gzdoom.git;protocol=http;nobranch=1"
SRCREV = "99aa489d09015a95bb78df2b30ede29f328cc874"

S = "${WORKDIR}/git"

inherit features_check pkgconfig

REQUIRED_DISTRO_FEATURES = "opengl"

PACKAGECONFIG ??= "${@bb.utils.filter("DISTRO_FEATURES", "vulkan", d)} \
    gles2 \
    "

PACKAGECONFIG[vulkan] = "-DHAVE_VULKAN=ON,-DHAVE_VULKAN=OFF,vulkan-loader vulkan-headers"
PACKAGECONFIG[gles2] = "-DHAVE_GLES2=ON,-DHAVE_GLES2=OFF"

require zdoom.inc
