SUMMARY = "Open Source Doom Engine (UZDoom)"
HOMEPAGE = "http://zdoom.org"
SECTION = "games"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=13fae2b1549cb50ba263396e9e947069"

inherit features_check pkgconfig

DEPENDS += "\
    bzip2 \
    libsdl2 \
    libvpx \
    libwebp \
    python3-native \
    waylandpp \
    zmusic \
"

PV = "4.14.3+git${SRCPV}"
SRC_URI = "git://github.com/UZDoom/UZDoom.git;protocol=http;nobranch=1"
SRCREV = "1cb7598afadbe7ffe753b2eaf1b887e58e988036"

S = "${WORKDIR}/git"

REQUIRED_DISTRO_FEATURES = "opengl wayland"

OECMAKE_ARGS += "\
    -DVULKAN_USE_WAYLAND=ON \
    -DSEND_ANON_STATS=OFF \
"

PACKAGECONFIG_GLES2 = ""
PACKAGECONFIG_GLES2:class-target = "gles2"
PACKAGECONFIG_JOYSTICK = ""
PACKAGECONFIG_JOYSTICK:class-target = "joystick"
PACKAGECONFIG_OPENAL = ""
PACKAGECONFIG_OPENAL:class-target = "openal"

PACKAGECONFIG ??= "\
    ${PACKAGECONFIG_OPENAL} \
    ${PACKAGECONFIG_JOYSTICK} \
    ${PACKAGECONFIG_GLES2} \
    ${@bb.utils.filter("DISTRO_FEATURES", "vulkan", d)} \
    ${@bb.utils.filter("DISTRO_FEATURES", "x11", d)} \
"

PACKAGECONFIG[openal] = "-DNO_OPENAL=OFF,-DNO_OPENAL=ON,openal-soft"
PACKAGECONFIG[gles2] = "-DHAVE_GLES2=ON,-DHAVE_GLES2=OFF"
PACKAGECONFIG[gtk+] = "-DNO_GTK=OFF,-DNO_GTK=ON,gtk+3"
PACKAGECONFIG[joystick] = "-DNO_SDL_JOYSTICK=OFF,-DNO_SDL_JOYSTICK=ON,"
PACKAGECONFIG[vulkan] = "DHAVE_VULKAN=ON,-DHAVE_VULKAN=OFF,vulkan-loader vulkan-headers"
PACKAGECONFIG[x11] = " -DVULKAN_USE_XLIB=ON,-DVULKAN_USE_XLIB=OFF,virtual/libx11"

do_install:append() {
    # No need for these files on an embedded system
    rm -rf \
        "${D}${datadir}/applications/" \
        "${D}${datadir}/metainfo/" \
        "${D}${datadir}/mime/" \
        "${D}${datadir}/icons/"
}

require zdoom.inc
