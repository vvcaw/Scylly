module.exports = {
    purge: [
        './frontend/**/*.html',
        './frontend/**/*.vue',
    ],
    darkMode: false, // or 'media' or 'class'
    theme: {
        extend: {
            colors: {
                background: '#1f2229',
                dark: '#30333c',
                light: '#373b45',
                lightB: '#96A9EE',
                darkB: '#8699db',
                spotify: '#1DB954',
                spotifyRed: '#b91d4e',
            },
            scale: {
              '101': '1.01'
            },
            transitionProperty: {
                'height': 'height',
                'spacing': 'margin, padding',
            }
        },
    },
    variants: {
        extend: {
            animation: ['hover', 'group-hover'],
            textColor: ['hover', 'group-hover'],
            scale: ['hover']
        },
    },
    plugins: [],
}
