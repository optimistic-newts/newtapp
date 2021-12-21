module.exports = {
  apps: [{
    name: 'newtapp',
    script: './src/index.js'
  }],
  deploy: {
    production: {
      user: 'admin',
      host: 'ec2-18-222-239-17.us-east-2.compute.amazonaws.com',
      key: '~/.ssh/newt.pem',
      ref: 'origin/main',
      repo: 'git@github.com:optimistic-newts/newtapp.git',
      path: '/home/admin/newtapp/',
      'post-deploy': 'npm install && pm2 startOrRestart ecosystem.config.js'
    }
  }
}
