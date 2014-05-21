from __future__ import print_function

import time

from .api import Twitter
from .oauth import OAuth

def auth_request_token():
    return ''

def oauth_dance(app_name, consumer_key, consumer_secret):
    """
    Perform the OAuth dance with some command-line prompts. Return the
    oauth_token and oauth_token_secret.

    Provide the name of your app in `app_name`, your consumer_key, and
    consumer_secret. This function will open a web browser to let the
    user allow your app to access their Twitter account. PIN
    authentication is used.
    """
    # (token, token_secret, consumer_key, consumer_secret)
    auth = OAuth('', '', consumer_key, consumer_secret)
    oauth_token, oauth_token_secret = parse_oauth_tokens(auth_request_token())
    print("""
In the web browser window that opens please choose to Allow
access. Copy the PIN number that appears on the next page and paste or
type it here:
""")
    oauth_url = ('https://api.twitter.com/oauth/authorize?oauth_token=' +
                 oauth_token)
    print("Opening: %s\n" % oauth_url)

    try:
        r = webbrowser.open(oauth_url)
        time.sleep(2) # Sometimes the last command can print some
                      # crap. Wait a bit so it doesn't mess up the next
                      # prompt.
        if not r:
            raise Exception()
    except:
        print("""
Uh, I couldn't open a browser on your computer. Please go here to get
your PIN:

""" + oauth_url)
    oauth_verifier = _input("Please enter the PIN: ").strip()
    twitter = Twitter(
        auth=OAuth(
            oauth_token, oauth_token_secret, consumer_key, consumer_secret),
        format='', api_version=None)
    oauth_token, oauth_token_secret = parse_oauth_tokens(
        twitter.oauth.access_token(oauth_verifier=oauth_verifier))
    return oauth_token, oauth_token_secret

def parse_oauth_tokens(result):
    for r in result.split('&'):
        k, v = r.split('=')
        if k == 'oauth_token':
            oauth_token = v
        elif k == 'oauth_token_secret':
            oauth_token_secret = v
    return oauth_token, oauth_token_secret
